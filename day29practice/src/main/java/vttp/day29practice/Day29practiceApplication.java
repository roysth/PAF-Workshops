package vttp.day29practice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import vttp.day29practice.model.Marvel;
import vttp.day29practice.service.MarvelService;

@SpringBootApplication
// public class Day29practiceApplication implements CommandLineRunner{
public class Day29practiceApplication {

	// @Autowired
	// private MarvelService marvelService;

	// @Override
	// public void run (String... args) {

	// 	List<Marvel> result = marvelService.search("doctor");
	// 	System.out.println(result);

	// 	System.exit(0);
	// }

	public static void main(String[] args) {
		SpringApplication.run(Day29practiceApplication.class, args);
	}

}
