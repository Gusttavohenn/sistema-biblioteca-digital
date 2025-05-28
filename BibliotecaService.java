// Arquivo: BibliotecaService.java
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BibliotecaService {
    private List<Livro> livros;
    private Map<String, List<Livro>> emprestimos;

    public BibliotecaService() {
        this.livros = new ArrayList<>();
        this.emprestimos = new HashMap<>();
        carregarDadosIniciais();
    }

    private void carregarDadosIniciais() {
        // Dados de exemplo
        adicionarLivro(new Livro("978-85-7522-567-8", "Clean Code", "Robert C. Martin", "Programação", 2008));
        adicionarLivro(new Livro("978-85-7522-123-4", "Java: Como Programar", "Harvey Deitel", "Programação", 2017));
        adicionarLivro(new Livro("978-85-7522-789-0", "O Senhor dos Anéis", "J.R.R. Tolkien", "Fantasia", 1954));
        adicionarLivro(new Livro("978-85-7522-456-7", "1984", "George Orwell", "Ficção", 1949));
        adicionarLivro(new Livro("978-85-7522-321-9", "Design Patterns", "Gang of Four", "Programação", 1994));
    }

    public boolean adicionarLivro(Livro livro) {
        if (livros.contains(livro)) {
            return false;
        }
        return livros.add(livro);
    }

    public boolean removerLivro(String isbn) {
        return livros.removeIf(livro -> livro.getIsbn().equals(isbn));
    }

    public List<Livro> buscarPorTitulo(String titulo) {
        return livros.stream()
                .filter(livro -> livro.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarPorAutor(String autor) {
        return livros.stream()
                .filter(livro -> livro.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livro> buscarPorCategoria(String categoria) {
        return livros.stream()
                .filter(livro -> livro.getCategoria().toLowerCase().contains(categoria.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Livro> obterTodosLivros() {
        return new ArrayList<>(livros);
    }

    public List<Livro> obterLivrosDisponiveis() {
        return livros.stream()
                .filter(Livro::isDisponivel)
                .collect(Collectors.toList());
    }

    public boolean emprestarLivro(String isbn, String usuario) {
        Optional<Livro> livroOpt = livros.stream()
                .filter(livro -> livro.getIsbn().equals(isbn) && livro.isDisponivel())
                .findFirst();

        if (livroOpt.isPresent()) {
            Livro livro = livroOpt.get();
            livro.setDisponivel(false);
            livro.setDataEmprestimo(LocalDate.now());
            livro.setUsuarioEmprestimo(usuario);

            emprestimos.computeIfAbsent(usuario, k -> new ArrayList<>()).add(livro);
            return true;
        }
        return false;
    }

    public boolean devolverLivro(String isbn) {
        Optional<Livro> livroOpt = livros.stream()
                .filter(livro -> livro.getIsbn().equals(isbn) && !livro.isDisponivel())
                .findFirst();

        if (livroOpt.isPresent()) {
            Livro livro = livroOpt.get();
            String usuario = livro.getUsuarioEmprestimo();

            livro.setDisponivel(true);
            livro.setDataEmprestimo(null);
            livro.setUsuarioEmprestimo(null);

            if (emprestimos.containsKey(usuario)) {
                emprestimos.get(usuario).remove(livro);
            }
            return true;
        }
        return false;
    }

    public Map<String, Integer> obterEstatisticas() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Total", livros.size());
        stats.put("Disponíveis", (int) livros.stream().filter(Livro::isDisponivel).count());
        stats.put("Emprestados", (int) livros.stream().filter(l -> !l.isDisponivel()).count());
        return stats;
    }

    public Set<String> obterCategorias() {
        return livros.stream()
                .map(Livro::getCategoria)
                .collect(Collectors.toSet());
    }
}