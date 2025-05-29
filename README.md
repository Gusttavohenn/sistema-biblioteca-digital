# 📚 Sistema de Biblioteca em Java

Este é um sistema simples de gerenciamento de biblioteca desenvolvido em Java com interface gráfica Swing. Ele permite cadastrar livros, gerenciar empréstimos e devoluções, e acompanhar estatísticas básicas da biblioteca.

---

## ✨ Funcionalidades

✅ Cadastro de livros com:

* ISBN
* Título
* Autor
* Categoria
* Ano de publicação

✅ Listagem de livros em uma tabela, mostrando:

* Status (Disponível / Emprestado)
* Usuário que realizou o empréstimo (se aplicável)

✅ Empréstimo e devolução de livros

✅ Contagem de estatísticas:

* Total de livros cadastrados
* Total de livros emprestados
* Total de livros disponíveis

✅ Pesquisa por título, autor ou ISBN

---

## 🏠 Estrutura do Projeto

* **Livro.java** → Classe que representa um livro com atributos como ISBN, título, autor, categoria, ano de publicação, status (disponível/emprestado), usuário do empréstimo e data do empréstimo.

* **BibliotecaService.java** → Classe de serviço que mantém a lista de livros, fornece métodos para cadastrar, emprestar, devolver e pesquisar livros.

* **BibliotecaGUI.java** → Interface gráfica construída com Swing, onde é possível interagir com o sistema: adicionar livros, emprestar, devolver, pesquisar e ver estatísticas.

---

## 🚀 Como Executar

1⃣ Certifique-se de ter o **Java 8 ou superior** instalado.

2⃣ Compile os arquivos:

```bash
javac Livro.java BibliotecaService.java BibliotecaGUI.java
```

3⃣ Execute o sistema:

```bash
java BibliotecaGUI
```

---

## 💻 Como Usar

* **Cadastrar Livro**: Clique no botão **Cadastrar Livro** e preencha os dados solicitados.
* **Emprestar Livro**: Selecione um livro disponível na tabela, clique em **Emprestar Livro** e informe o nome do usuário.
* **Devolver Livro**: Selecione um livro emprestado na tabela e clique em **Devolver Livro**.
* **Pesquisar**: Use a caixa de pesquisa para encontrar livros por título, autor ou ISBN.
* **Ver Estatísticas**: Abaixo da tabela, veja os contadores atualizados automaticamente.

---

## 🛠️ Tecnologias Utilizadas

* Java
* Swing (interface gráfica)
* Coleções (ArrayList)
* java.time.LocalDate para gerenciar datas

---

## 📦 Possíveis Melhorias Futuras

* Adicionar data de devolução e cálculo de dias restantes/vencidos
* Permitir exportação e importação de dados em CSV ou JSON
* Implementar persistência em banco de dados
* Melhorar a interface gráfica com um visual mais moderno
