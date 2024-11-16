package com.example.ScreenSoundMusicas;

import com.example.ScreenSoundMusicas.model.Artista;
import com.example.ScreenSoundMusicas.model.Musica;
import com.example.ScreenSoundMusicas.model.Tipo;
import com.example.ScreenSoundMusicas.repository.ArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class ScreenSoundMusicasApplication implements CommandLineRunner {

	final int OPCAO_MENU_CADASTRAR_ARTISTA = 1;
	final int OPCAO_MENU_CADASTRAR_MUSICAS = 2;
	final int OPCAO_MENU_LISTAR_MUSICAS = 3;
	final int OPCAO_MENU_BUSCAR_MUSICAS_POR_ARTISTAS = 4;
	final int OPCA0_MENU_SAIR = 0;

	@Autowired
	private ArtistaRepository artistaRepository;

	int opcaoMenu;
	Scanner scanner = new Scanner(System.in);
	Artista artista;

	public static void main(String[] args) {
		SpringApplication.run(ScreenSoundMusicasApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		do {
			mostraMenu();
			opcaoMenu = scanner.nextInt();
			switch (opcaoMenu) {
				case OPCAO_MENU_CADASTRAR_ARTISTA: cadastrarArtista(); break;
				case OPCAO_MENU_CADASTRAR_MUSICAS: cadastrarMusica(); break;
				case OPCAO_MENU_LISTAR_MUSICAS: listarMusicas(); break;
				case OPCAO_MENU_BUSCAR_MUSICAS_POR_ARTISTAS: buscarMusicasPorArtista(); break;
			}
		}
		while (opcaoMenu != 0);
	}
	private void mostraMenu() {
		System.out.println(OPCAO_MENU_CADASTRAR_ARTISTA + " - Cadastrar artista");
		System.out.println(OPCAO_MENU_CADASTRAR_MUSICAS + " - Cadastrar músicas");
		System.out.println(OPCAO_MENU_LISTAR_MUSICAS + " - Listar músicas");
		System.out.println(OPCAO_MENU_BUSCAR_MUSICAS_POR_ARTISTAS + " - Buscar músicas por artista");
		System.out.println(OPCA0_MENU_SAIR + " - Sair");
	}

	private void cadastrarArtista() {
		Artista artista = new Artista();
		scanner.nextLine();
		System.out.println("Informe o nome do artista:");
		artista.setNome(scanner.nextLine());
		for (Tipo tipo : Tipo.values()) {
			System.out.println(tipo.descricao);
		}
		System.out.println("Informe o tipo do artista:");
		artista.setTipo(Tipo.fromString(scanner.nextLine()));

		artistaRepository.save(artista);
	}

	private void buscarArtistaPeloId() {
		artistaRepository.findAll().stream()
				.sorted(Comparator.comparing(Artista::getNome))
				.forEach(System.out::println);
		System.out.println("Informe o id do artista:");
		Long idArtista = scanner.nextLong();
		artista = null;
		Optional<Artista> optionalArtista = artistaRepository.findById(idArtista);
		if (optionalArtista.isEmpty()) {
			System.out.println("Artista não encontrado");
			return;
		}
		artista = optionalArtista.get();
	}
	private void cadastrarMusica() {
		buscarArtistaPeloId();
		if (artista == null) {
			return;
		}

		Musica musica = new Musica();
		musica.setArtista(artista);
		scanner.nextLine();
		System.out.println("Informe o titulo da musica:");
		musica.setTitulo(scanner.nextLine());
		artista.getMusicas().add(musica);
		artistaRepository.save(artista);
	}
	private void listarMusicas() {
		artistaRepository.findAll().forEach(a-> a.getMusicas().stream().forEach(System.out::println));
	}

	private void buscarMusicasPorArtista() {
		buscarArtistaPeloId();
		if (artista == null) {
			return;
		}
		artista.getMusicas().stream().sorted(Comparator.comparing(Musica::getTitulo)).forEach(System.out::println);
	}
}
