/**
 * Created by jamesmueller on 7/20/14.
 */
class CreateBigCsvFile {

    public static void main(String... args) {
        def i = 0

        def file = new File("target/inventory/bigfile.csv")

        if(file.exists()) {
            file.delete()
        }

        int lines = 100000
        if (args.length == 1) {
            lines = Integer.parseInt(args[0])
        }

        println "Creating an inventory csv file with $lines lines"
        file.withWriter { out ->
            (1..lines).each { line ->
                out.write("123,$line,bumper,4\n")
            }
        }

    }
}
