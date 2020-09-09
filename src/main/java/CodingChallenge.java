import java.io.*;

class CodingChallenge {

    public static void main(String[] args) {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        long startTime = System.nanoTime();
        File dir = new File("/Users/agarwal/Documents/FinraCodingChallenge/src/main/resources");
        File[] files = dir.listFiles();
        for (File file : files) {
            // Writer w1 = new Writer(file, destination);
            Writer w1 = new Writer(file, new File(file + ".lwr"));
            Thread t = new Thread(w1);
            t.setPriority(Thread.MAX_PRIORITY);
            t.start();

        }
        long stopTime = System.nanoTime();

        System.out.println("Total execution time is " + (stopTime - startTime));

    }
}

class Writer implements Runnable {

    File source;
    File destination;

    public Writer(File source, File destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public void run() {
        String content;
        content = readFromFile(source.getAbsolutePath());
        writeToFile(destination, content);
    }

    private static void writeToFile(File file, String content) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static String readFromFile(String filename) {
        StringBuffer content = new StringBuffer();
        try {
            String text;
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            while ((text = reader.readLine()) != null) {
                text = ignoreCaps(text);
                content.append(text);
                content.append("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    static String ignoreCaps(String text) {
        String words[] = text.split(" ");
        StringBuffer lowCaseWord = new StringBuffer();
        for (int i = 0; i < words.length; i++) {
            String word = words[i].trim();
            boolean flag = false;
            for (int chr = 0; chr < word.length(); chr++) {
                if (Character.isLowerCase(word.charAt(chr))) {
                    flag = true;
                    lowCaseWord.append(word.charAt(chr));
                }
            }
            if (flag) {
                lowCaseWord.append(' ');
            }
        }
        return lowCaseWord.toString();
    }
}
