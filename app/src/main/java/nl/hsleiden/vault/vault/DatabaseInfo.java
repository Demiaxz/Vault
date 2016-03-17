package nl.hsleiden.vault.vault;

/**
 * Created by Perseus on 09-03-16.
 */
public class DatabaseInfo {

    public class CourseTables {
        public static final String COURSE = "course";		// NAAM VAN JE TABEL
    }

    public class CourseColumn {
        public static final String NAME = "name";        // VASTE WAARDES
        public static final String ECTS = "ects";        // NAAM VAN DE KOLOMMEN
        public static final String GRADE = "grade";           // CIJFER
        public static final String PERIOD = "period";     // periode (1/2/3/4)
        public static final String TESTDATE = "testdate"; //test date
        public static final String DESCRIPTION = "description"; //description
        public static final String IMPACT = "impact"; // 30-70% or 1
        public static final String CONCEPT = "concept";
        public static final String MUTATIONDATE = "mutationdate";

    }

}
