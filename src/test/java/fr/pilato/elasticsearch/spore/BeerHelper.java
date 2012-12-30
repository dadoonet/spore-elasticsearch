package fr.pilato.elasticsearch.spore;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class BeerHelper {

	public static Beer generate() {

		return new Beer(generateBrand(), generateColour(), Math.random()*2, Math.random()*10);
		
	}

	private static String generateBrand() {

		Long result = Math.round(Math.random() * 2);

		switch (result.intValue()) {
		case 0:
			return "Heineken";
		case 1:
			return "Grimbergen";
		case 2:
			return "Kriek";
		default:
			break;
		}

		return null;
	}
	
	
	private static Colour generateColour() {

		Long result = Math.round(Math.random() * 2);

		switch (result.intValue()) {
		case 0:
			return Colour.DARK;
		case 1:
			return Colour.PALE;
		case 2:
			return Colour.WHITE;
		default:
			break;
		}

		return null;
	}

    public static String toJsonString(Beer beer) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(beer);
        } catch (IOException e) {
            return ((Object) beer).toString();
        }
    }
}
