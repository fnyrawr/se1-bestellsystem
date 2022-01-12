package application;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Play_C2_Streams {

    Play_C2_Streams() {

    }

    public static void main(String[] args) {
        System.out.println( "Filter names by length and alphabetical if same length:" );
        Stream<String> names = List.of("Hendricks", "Raymond", "Pena", "Gonzalez",
                "Nielsen", "Hamilton", "Graham", "Gill", "Vance", "Howe", "Ray", "Talley",
                "Brock", "Hall", "Gomez", "Bernard", "Witt", "Joyner", "Rutledge", "Petty",
                "Strong", "Soto", "Duncan", "Lott", "Case", "Richardson", "Crane", "Cleveland",
                "Casey", "Buckner", "Hardin", "Marquez", "Navarro").stream();

        // sort by length
        names
                .sorted()
                .sorted( (s1, s2) -> Integer.compare( s1.length(), s2.length() ) )
                .forEach( n -> System.out.print( n + ", " ) );

        // stream again, since previous stream already got consumed
        names = List.of("Hendricks", "Raymond", "Pena", "Gonzalez",
                "Nielsen", "Hamilton", "Graham", "Gill", "Vance", "Howe", "Ray", "Talley",
                "Brock", "Hall", "Gomez", "Bernard", "Witt", "Joyner", "Rutledge", "Petty",
                "Strong", "Soto", "Duncan", "Lott", "Case", "Richardson", "Crane", "Cleveland",
                "Casey", "Buckner", "Hardin", "Marquez", "Navarro").stream();

        System.out.println( "\nFilter names and print only names ending with ez:" );

        // filter "ez"
        List<String> ezNames = names.filter( name -> name.matches(".*ez" ) ).collect(Collectors.toList());

        for( String name: ezNames ) {
            System.out.print( name + ", " );
        }
    }
}
