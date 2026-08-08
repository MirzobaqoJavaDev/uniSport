package uz.uniSport.uni_sport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * UniSport loyihasining asosiy ishga tushirish (Main) klassi.
 * Bu klass Spring Boot ilovasini boshlaydi.
 */
@SpringBootApplication
public class UniSportApplication {

	/**
	 * Ilovani ishga tushiruvchi asosiy (main) metod.
	 *
	 * @param args dasturga buyruqlar qatori orqali beriladigan argumentlar (command line arguments)
	 */
	public static void main(String[] args) {
		SpringApplication.run(UniSportApplication.class, args);
	}

}
