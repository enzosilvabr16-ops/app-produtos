package Cotiinformatica.services;

import Cotiinformatica.entities.Categoria;
import Cotiinformatica.interfaces.CategoriaService;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CategoriaServiceImpl implements CategoriaService {

    private final String arquivo = "categoria.txt";

    @Override
    public void cadastrar(Categoria categoria) {

        categoria.setId(UUID.randomUUID());

        var scan = new Scanner(System.in);

        System.out.println("CADASTRO DE CATEGORIA");

        System.out.println("\nINFORME O NOME:");
        categoria.setNome(scan.nextLine());

        System.out.println("INFORME A DESCRIÇÃO:");
        categoria.setDescricao(scan.nextLine());

        try(var printWriter = new PrintWriter(new FileWriter(arquivo, true))) {
            printWriter.write(categoria.getId() + "\n");
            printWriter.write(categoria.getNome() + "\n");
            printWriter.write(categoria.getDescricao() + "\n");
            printWriter.write("...");
        }
        catch (Exception e) {
            System.err.println("FALHA AO GRAVAR ARQUIVO : " + e.getMessage());
        }
    }

    @Override
    public List<Categoria> consultar() {

        System.out.println("\nCONSULTA DE CATEGORIAS:\n");

        var categorias = new ArrayList<Categoria>();

        try (var scanner = new Scanner(new File(arquivo))) {

            //Ler cada linha do arquivo
            while(scanner.hasNextLine()) {

                //Ler a linha do arquivo
                var linha = scanner.nextLine().trim();

                // Remove o separador, inclusive dos registros antigos
                // em que "..." ficou junto ao ID da próxima categoria.
                if (linha.startsWith("...")) {
                    linha = linha.substring(3).trim();
                }

                if (linha.isEmpty()) {
                    continue;
                }

                var categoria = new Categoria();

                categoria.setId(UUID.fromString(linha));
                categoria.setNome(scanner.nextLine());
                categoria.setDescricao(scanner.nextLine());

                categorias.add(categoria);
            }
        }
        catch(Exception e) {
            System.out.println("\nFALHA AO LER ARQUIVO: " + e.getMessage());
        }

        return categorias;
    }
}
