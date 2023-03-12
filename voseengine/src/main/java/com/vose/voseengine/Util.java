package com.vose.voseengine;

import com.vose.voseengine.model.entity.Identable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class Util {
    private static final Logger log = LoggerFactory.getLogger(Util.class);

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String monthKeyTolerated() {
        LocalDate date = LocalDate.now();
        return monthKeyTolerated(date);
    }

    public static String monthKeyTolerated(Date given) {
        LocalDate date = LocalDate.parse(given.toString().substring(0, 10));
        return monthKeyTolerated(date);
    }

    private static String monthKeyTolerated(LocalDate date) {
        // Ay sonu işlemlerinde kullanılıyor.
        // Ay sonu işlemleri son dk'da yapıldı ve işlemler sırasında elde edilen tarih ayın başarı ise
        // asıl elde edilmek istenen son ay olacağı için bir kaç günlük tolerans eklendi.
        int day = date.getDayOfMonth();
        if(day < 2) date.minusDays(3);
        int month = date.getMonthValue();
        int year = date.getYear();
        return String.format("%02d%02d", month, year%100);
    }

    public static Date startOfMonthByKey(String monthKey) {
        try {
            String month = monthKey.substring(0, 2);
            String year = monthKey.substring(2, 4);
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = String.format("20%s-%s-01", year, month);
            return parser.parse(dateStr);
        }
        catch (Exception e) {
            log.error("Cannot get date of start of month {}.", monthKey, e);
            return null;
        }
    }

    public static Date startOfMonthTolerated(Date given) {
        LocalDate date = LocalDate.parse(given.toString().substring(0,10));
        return startOfMonthTolerated(date);
    }

    private static Date startOfMonthTolerated(LocalDate date) {
        try {
            // Ay sonu işlemlerinde kullanılıyor.
            // Ay sonu işlemleri son dk'da yapıldı ve işlemler sırasında elde edilen tarih ayın başı ise
            // asıl elde edilmek istenen son ay olacağı için bir kaç günlük tolerans eklendi.
            int day = date.getDayOfMonth();
            if(day < 3) date.minusDays(3);
            int month = date.getMonthValue();
            int year = date.getYear();
            SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
            String monthStartStr = String.format("%d-%02d-01", year, month);
            return parser.parse(monthStartStr);
        }
        catch (Exception e) {
            log.error("Cannot get date of start of month.", e);
            return null;
        }
    }

    private static final String[] months = new String[]{"Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"};
    public static String localeMonthText(String monthKey) {
        int month = Integer.parseInt(monthKey.substring(0,2));
        String year = monthKey.substring(2,4);
        return months[month-1]+" 20"+year;
    }

    public static void safeSleep(long millis) {
        int chunkCount = (int)(millis/100);
        for(int i = 0; i < chunkCount; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static double normalizeDouble(double num) {
        return Math.round(num*100)/100.0;
    }

    public static String randomStr(int len) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static final char INVALID_CHARS[] = { ' ', 'ı', 'ğ', 'ü', 'ş', 'ö', 'ç' };
    private static final char SANITIZED_CHARS[] = { '_', 'i', 'g', 'u', 's', 'o', 'c' };

    public static String sanitizeFileName(String filename) {

        filename = filename.toLowerCase();
        if(filename.length() > 80) {
            filename = filename.substring(0, 80);
        }
        for (int i = 0; i < INVALID_CHARS.length; i++) {
            if (filename.indexOf(INVALID_CHARS[i]) != -1) {
                filename = filename.replace(INVALID_CHARS[i], SANITIZED_CHARS[i]);
            }
        }

        return filename;
    }

    public static String generateFilename(String origin) {
        int extensionDotIndex = origin.lastIndexOf('.');
        String filenameWoext = sanitizeFileName(origin.substring(0, extensionDotIndex));
        String extension = origin.substring(extensionDotIndex+1);
        String filename = filenameWoext + '-' + Util.randomStr(10) + '.' + extension;
        return filename;
    }

    public static <T extends Identable<ID>, ID> Map<ID, T> convertToMap(List<T> list) {
        Map<ID, T> result = new HashMap<>();
        for(T item : list) {
            result.put(item.getId(), item);
        }
        return result;
    }

    public static int compareDoubles(double a, double b) {
        return BigDecimal.valueOf(normalizeDouble(a)).compareTo(BigDecimal.valueOf(normalizeDouble(b)));
    }
}
