// Arquivo: BibliotecaGUI.java
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class BibliotecaGUI extends JFrame {
    private BibliotecaService service;
    private JTable tabelaLivros;
    private DefaultTableModel modeloTabela;
    private JTextField campoTitulo, campoAutor, campoISBN, campoCategoria, campoAno;
    private JTextField campoBusca;
    private JComboBox<String> comboBusca, comboCategoria;
    private JLabel labelEstatisticas;

    public BibliotecaGUI() {
        service = new BibliotecaService();
        inicializarInterface();
        atualizarTabela();
        atualizarEstatisticas();
    }

    private void inicializarInterface() {
        setTitle("Sistema de Biblioteca Digital - Gustavo Oliveira");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Layout principal
        setLayout(new BorderLayout());

        // Painel superior - Busca e Estatísticas
        JPanel painelSuperior = criarPainelBusca();
        add(painelSuperior, BorderLayout.NORTH);

        // Painel central - Tabela
        JPanel painelCentral = criarPainelTabela();
        add(painelCentral, BorderLayout.CENTER);

        // Painel inferior - Formulário e Ações
        JPanel painelInferior = criarPainelFormulario();
        add(painelInferior, BorderLayout.SOUTH);

        // Menu
        setJMenuBar(criarMenuBar());
    }

    private JPanel criarPainelBusca() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(new TitledBorder("Busca e Estatísticas"));
        painel.setBackground(new Color(240, 248, 255));

        // Painel de busca
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBusca.setBackground(new Color(240, 248, 255));

        painelBusca.add(new JLabel("Buscar por:"));
        comboBusca = new JComboBox<>(new String[]{"Título", "Autor", "Categoria"});
        painelBusca.add(comboBusca);

        campoBusca = new JTextField(20);
        painelBusca.add(campoBusca);

        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(new Color(70, 130, 180));
        btnBuscar.setForeground(Color.BLACK);
        btnBuscar.addActionListener(e -> realizarBusca());
        painelBusca.add(btnBuscar);

        JButton btnLimpar = new JButton("Mostrar Todos");
        btnLimpar.addActionListener(e -> atualizarTabela());
        painelBusca.add(btnLimpar);

        // Painel de estatísticas
        labelEstatisticas = new JLabel();
        labelEstatisticas.setFont(new Font("Arial", Font.BOLD, 12));
        labelEstatisticas.setForeground(new Color(25, 25, 112));

        painel.add(painelBusca, BorderLayout.WEST);
        painel.add(labelEstatisticas, BorderLayout.EAST);

        return painel;
    }

    private JPanel criarPainelTabela() {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(new TitledBorder("Acervo da Biblioteca"));

        String[] colunas = {"ISBN", "Título", "Autor", "Categoria", "Ano", "Status", "Usuário"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaLivros = new JTable(modeloTabela);
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaLivros.getTableHeader().setBackground(new Color(70, 130, 180));
        tabelaLivros.getTableHeader().setForeground(Color.BLACK);
        tabelaLivros.setRowHeight(25);

        // Cores alternadas nas linhas
        tabelaLivros.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabelaLivros);
        painel.add(scrollPane, BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelFormulario() {
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBorder(new TitledBorder("Gerenciamento de Livros"));
        painelPrincipal.setBackground(new Color(248, 248, 255));

        // Formulário
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(new Color(248, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Campos do formulário
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        campoISBN = new JTextField(15);
        painelForm.add(campoISBN, gbc);

        gbc.gridx = 2; 
        painelForm.add(new JLabel("Título:"), gbc);
        gbc.gridx = 3;
        campoTitulo = new JTextField(20);
        painelForm.add(campoTitulo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelForm.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1;
        campoAutor = new JTextField(15);
        painelForm.add(campoAutor, gbc);

        gbc.gridx = 2;
        painelForm.add(new JLabel("Categoria:"), gbc);
        gbc.gridx = 3;
        comboCategoria = new JComboBox<>(new String[]{"Programação", "Ficção", "Fantasia", "Ciência", "História", "Biografia", "Outro"});
        comboCategoria.setEditable(true);
        painelForm.add(comboCategoria, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1;
        campoAno = new JTextField(15);
        painelForm.add(campoAno, gbc);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.setBackground(new Color(248, 248, 255));

        JButton btnAdicionar = new JButton("Adicionar Livro");
        btnAdicionar.setBackground(new Color(34, 139, 34));
        btnAdicionar.setForeground(Color.BLACK);
        btnAdicionar.addActionListener(e -> adicionarLivro());

        JButton btnRemover = new JButton("Remover Livro");
        btnRemover.setBackground(new Color(220, 20, 60));
        btnRemover.setForeground(Color.BLACK);
        btnRemover.addActionListener(e -> removerLivro());

        JButton btnEmprestar = new JButton("Emprestar");
        btnEmprestar.setBackground(new Color(255, 140, 0));
        btnEmprestar.setForeground(Color.BLACK);
        btnEmprestar.addActionListener(e -> emprestarLivro());

        JButton btnDevolver = new JButton("Devolver");
        btnDevolver.setBackground(new Color(30, 144, 255));
        btnDevolver.setForeground(Color.BLACK);
        btnDevolver.addActionListener(e -> devolverLivro());

        JButton btnLimparForm = new JButton("Limpar");
        btnLimparForm.addActionListener(e -> limparFormulario());

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnEmprestar);
        painelBotoes.add(btnDevolver);
        painelBotoes.add(btnLimparForm);

        painelPrincipal.add(painelForm, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        return painelPrincipal;
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));
        menuArquivo.add(itemSair);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        itemSobre.addActionListener(e -> mostrarSobre());
        menuAjuda.add(itemSobre);

        menuBar.add(menuArquivo);
        menuBar.add(menuAjuda);

        return menuBar;
    }

    private void realizarBusca() {
        String termo = campoBusca.getText().trim();
        if (termo.isEmpty()) {
            atualizarTabela();
            return;
        }

        String tipoBusca = (String) comboBusca.getSelectedItem();
        List<Livro> resultados;

        switch (tipoBusca) {
            case "Título":
                resultados = service.buscarPorTitulo(termo);
                break;
            case "Autor":
                resultados = service.buscarPorAutor(termo);
                break;
            case "Categoria":
                resultados = service.buscarPorCategoria(termo);
                break;
            default:
                resultados = service.obterTodosLivros();
        }

        atualizarTabela(resultados);
    }

    private void adicionarLivro() {
        try {
            String isbn = campoISBN.getText().trim();
            String titulo = campoTitulo.getText().trim();
            String autor = campoAutor.getText().trim();
            String categoria = (String) comboCategoria.getSelectedItem();
            int ano = Integer.parseInt(campoAno.getText().trim());

            if (isbn.isEmpty() || titulo.isEmpty() || autor.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Livro livro = new Livro(isbn, titulo, autor, categoria, ano);
            if (service.adicionarLivro(livro)) {
                JOptionPane.showMessageDialog(this, "Livro adicionado com sucesso!");
                limparFormulario();
                atualizarTabela();
                atualizarEstatisticas();
            } else {
                JOptionPane.showMessageDialog(this, "Livro com este ISBN já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ano deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para remover!");
            return;
        }

        String isbn = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        int resposta = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover este livro?", "Confirmar", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            if (service.removerLivro(isbn)) {
                JOptionPane.showMessageDialog(this, "Livro removido com sucesso!");
                atualizarTabela();
                atualizarEstatisticas();
            }
        }
    }

    private void emprestarLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para emprestar!");
            return;
        }

        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 5);
        if ("Emprestado".equals(status)) {
            JOptionPane.showMessageDialog(this, "Este livro já está emprestado!");
            return;
        }

        String usuario = JOptionPane.showInputDialog(this, "Nome do usuário:");
        if (usuario != null && !usuario.trim().isEmpty()) {
            String isbn = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
            if (service.emprestarLivro(isbn, usuario.trim())) {
                JOptionPane.showMessageDialog(this, "Livro emprestado com sucesso!");
                atualizarTabela();
                atualizarEstatisticas();
            }
        }
    }

    private void devolverLivro() {
        int linhaSelecionada = tabelaLivros.getSelectedRow();
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um livro para devolver!");
            return;
        }

        String status = (String) modeloTabela.getValueAt(linhaSelecionada, 5);
        if ("Disponível".equals(status)) {
            JOptionPane.showMessageDialog(this, "Este livro já está disponível!");
            return;
        }

        String isbn = (String) modeloTabela.getValueAt(linhaSelecionada, 0);
        if (service.devolverLivro(isbn)) {
            JOptionPane.showMessageDialog(this, "Livro devolvido com sucesso!");
            atualizarTabela();
            atualizarEstatisticas();
        }
    }

    private void limparFormulario() {
        campoISBN.setText("");
        campoTitulo.setText("");
        campoAutor.setText("");
        campoAno.setText("");
        comboCategoria.setSelectedIndex(0);
    }

    private void atualizarTabela() {
        atualizarTabela(service.obterTodosLivros());
    }

    private void atualizarTabela(List<Livro> livros) {
        modeloTabela.setRowCount(0);
        for (Livro livro : livros) {
            Object[] linha = {
                livro.getIsbn(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getCategoria(),
                livro.getAnoPublicacao(),
                livro.isDisponivel() ? "Disponível" : "Emprestado",
                    livro.getUsuarioEmprestimo() != null ? livro.getUsuarioEmprestimo() : ""
            };
            modeloTabela.addRow(linha);
        }
    }

    private void atualizarEstatisticas() {
        Map<String, Integer> stats = service.obterEstatisticas();
        String texto = String.format("Total: %d | Disponíveis: %d | Emprestados: %d", 
                                    stats.get("Total"), stats.get("Disponíveis"), stats.get("Emprestados"));
        labelEstatisticas.setText(texto);
    }

    private void mostrarSobre() {
        String mensagem = "Sistema de Biblioteca Digital v1.0\n\n" +
                         "Desenvolvido em Java com Swing\n" +
                         "Funcionalidades:\n" +
                         "• Cadastro de livros\n" +
                         "• Busca por título, autor e categoria\n" +
                         "• Controle de empréstimos\n" +
                         "• Estatísticas do acervo\n\n" +
                         "Criado para demonstração de habilidades em Java";
        
        JOptionPane.showMessageDialog(this, mensagem, "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new BibliotecaGUI().setVisible(true);
        });
    }
}