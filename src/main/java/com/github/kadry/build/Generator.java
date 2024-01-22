package com.github.kadry.build;

import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Generator {

    private static final Random random = new Random();
    public static Object randomValue(Class<?> type)
            throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (type.equals(Byte.class)) return randomByte();
        if(type.equals(Short.class)) return randomShort();
        if (type.equals(Integer.class)) return randomInt();
        if (type.equals(Long.class)) return randomLong();
        if (type.equals(Float.class)) return randomFloat();
        if (type.equals(Double.class)) return randomDouble();
        if (type.equals(Boolean.class)) return randomBoolean();
        if (type.equals(Character.class)) return randomCharacter();
        if (type.equals(String.class)) return randomString();
        if (type.equals(LocalDate.class)) return randomLocalDate();
        if (type.equals(LocalTime.class)) return randomLocalTime();
        if (type.equals(LocalDateTime.class)) return randomLocalDateTime();
        if (type.equals(Calendar.class)) return randomCalender();
        if (type.equals(Date.class)) return randomDate();
        if (type.equals(java.sql.Date.class)) return randomSqlDate();
        if (type.equals(Time.class)) return randomSqlTime();
        if (type.equals(Timestamp.class)) return randomSqlTimestamp();
        if (type.isEnum()) return randomEnumValue(type);
        return createNewInstance(type); // Return a new instance as placeholder
    }

    // Constraint: Target class must have a default constructor
    private static Object createNewInstance(Class<?> type)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return type.getDeclaredConstructor().newInstance();
    }

    private static Object randomEnumValue(Class<?> type) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object[] enums = (Object[]) type.getMethod("values").invoke(type);
        return enums[random.nextInt(enums.length)];
    }

    private static Timestamp randomSqlTimestamp() {
        long offset = Timestamp.valueOf("2000-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2023-12-31 00:00:00").getTime();
        long diff = end - offset + 1;
        long randomTime = offset + (long) (Math.random() * diff);
        return new Timestamp(randomTime);
    }

    private static Time randomSqlTime() {
        long offset = Time.valueOf("00:00:00").getTime();
        long end = Time.valueOf("23:59:59").getTime();
        long diff = end - offset + 1;
        long randomTime = offset + (long) (Math.random() * diff);
        return new Time(randomTime);
    }

    private static java.sql.Date randomSqlDate() {
        long offset = java.sql.Date.valueOf("2000-01-01").getTime();
        long end = java.sql.Date.valueOf("2023-12-31").getTime();
        long diff = end - offset + 1;
        long randomTime = offset + (long) (Math.random() * diff);
        return new java.sql.Date(randomTime);
    }

    private static Date randomDate() {
        long offset = Timestamp.valueOf("2000-01-01 00:00:00").getTime();
        long end = Timestamp.valueOf("2023-12-31 00:00:00").getTime();
        long diff = end - offset + 1;
        long randomTime = offset + (long) (Math.random() * diff);
        return new Date(randomTime);
    }

    private static Calendar randomCalender() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(randomDate());
        return calendar;
    }

    private static LocalDateTime randomLocalDateTime() {
        return LocalDateTime.of(randomLocalDate(), randomLocalTime());
    }

    private static LocalDate randomLocalDate() {
        long offset = LocalDate.of(1970, 1, 1).toEpochDay();
        long end = LocalDate.of(2023, 12, 31).toEpochDay();
        long diff = end - offset + 1;
        long randomDate = offset + (long) (Math.random() * diff);
        return LocalDate.ofEpochDay(randomDate);
    }

    private static LocalTime randomLocalTime() {
        long offset = LocalTime.of(0, 0, 0).toNanoOfDay();
        long end = LocalTime.of(23, 59, 59).toNanoOfDay();;
        long diff = end - offset + 1;
        long randomNanoOfDay = offset + (long) (Math.random() * diff);
        return LocalTime.ofNanoOfDay(randomNanoOfDay);
    }

    // Generate a random string of max length of 10 and min length of 5
    private static String randomString() {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < 5 + random.nextInt(6); i++) {
            randomString.append(randomCharacter());
        }
        return randomString.toString();
    }

    private static Character randomCharacter() {
        return (char) (' ' + random.nextInt(94)); // Generates ASCII characters only
    }

    private static Boolean randomBoolean() {
        return random.nextBoolean();
    }

    private static Double randomDouble() {
        return random.nextDouble();
    }

    private static Float randomFloat() {
        return random.nextFloat();
    }

    private static Long randomLong() {
        return random.nextLong();
    }

    private static Short randomShort() {
        return (short) random.nextInt();
    }

    private static Byte randomByte() {
        return (byte) random.nextInt();
    }

    private static int randomInt(){ return random.nextInt();}
}
