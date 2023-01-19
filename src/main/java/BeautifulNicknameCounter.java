import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class BeautifulNicknameCounter {
    public static AtomicInteger len3 = new AtomicInteger();
    public static AtomicInteger len4 = new AtomicInteger();
    public static AtomicInteger len5 = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String t : texts) {
                if (isPalindrome(t)) {
                    adder(t);
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            for (String t : texts) {
                if (repeats(t)) {
                    adder(t);
                }
            }
        });
        Thread thread3 = new Thread(() -> {
            for (String t : texts) {
                if (increase(t)) {
                    adder(t);
                }
            }
        });
        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + len3);
        System.out.println("Красивых слов с длиной 4: " + len4);
        System.out.println("Красивых слов с длиной 5: " + len5);

    }

    public static boolean isPalindrome(String string) {
        return string.equals(new StringBuffer(string).reverse().toString());
    }

    public static boolean repeats(String string) {
        char[] charArray = string.toCharArray();
        int count = 0;
        for (char c : charArray) {
            if (charArray[0] == c)
                count++;
        }
        return charArray.length == count;
    }

    public static boolean increase(String string) {
        char[] charArray = string.toCharArray();
        int count = 0;
        int index = 0;
        for (int i = 1; i < charArray.length; i++) {
            if (charArray[index] < charArray[i]) {
                index++;
                count++;
            }
        }
        return charArray.length - 1 == count;
    }

    public static void adder(String t) {
        if (t.length() == 5)
            len5.incrementAndGet();
        if (t.length() == 4)
            len4.incrementAndGet();
        if (t.length() == 3)
            len3.incrementAndGet();

    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
