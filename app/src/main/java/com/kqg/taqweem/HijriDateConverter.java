package com.kqg.taqweem;

public class HijriDateConverter {
    public static final String[] hijriMonths = {
            "محرم", "صفر", "ربيع الأول", "ربيع الثاني",
            "جمادى الأولى", "جمادى الآخرة", "رجب", "شعبان",
            "رمضان", "شوال", "ذو القعدة", "ذو الحجة"
    };

    public String gregorianToHijri(int day, int month, int year) {
        double jd = gregorianToJulian(day, month, year);
        double hijriJD = jd - 1948439.5;
        int hijriYear = (int) (hijriJD / 354.366);
        double remaining = hijriJD - hijriYear * 354.366;
        int hijriMonth = (int) (remaining / 29.53) + 1;
        int hijriDay = (int) (remaining % 29.53) + 1;

        if (hijriDay > 30) {
            hijriDay = 1;
            hijriMonth++;
        }
        if (hijriMonth > 12) {
            hijriMonth = 1;
            hijriYear++;
        }

        return hijriDay + " " + hijriMonths[hijriMonth-1] + " " + hijriYear + " هـ";
    }

    public String hijriToGregorian(int day, int month, int year) {
        double hijriJD = 1948439.5 + (year * 354.366) + ((month-1) * 29.53) + day;
        int[] gregDate = julianToGregorian((int) hijriJD);
        return gregDate[0] + "/" + gregDate[1] + "/" + gregDate[2];
    }

    private double gregorianToJulian(int day, int month, int year) {
        if (month <= 2) {
            year--;
            month += 12;
        }
        int a = year / 100;
        int b = 2 - a + a / 4;
        return Math.floor(365.25 * (year + 4716)) + Math.floor(30.6001 * (month + 1)) + day + b - 1524.5;
    }

    private int[] julianToGregorian(double jd) {
        int l = (int) (jd + 68569);
        int n = 4 * l / 146097;
        l = l - (146097 * n + 3) / 4;
        int i = 4000 * (l + 1) / 1461001;
        l = l - 1461 * i / 4 + 31;
        int j = 80 * l / 2447;
        int day = l - 2447 * j / 80;
        l = j / 11;
        int month = j + 2 - 12 * l;
        int year = 100 * (n - 49) + i + l;
        return new int[]{day, month, year};
    }
}