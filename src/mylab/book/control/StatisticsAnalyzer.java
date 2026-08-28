package mylab.book.control;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import mylab.book.entity.Magazine;
import mylab.book.entity.Novel;
import mylab.book.entity.Publication;
import mylab.book.entity.ReferenceBook;

public class StatisticsAnalyzer {

    public Map<String, Double> calculateAveragePriceByType(
            Publication[] publications) {

        Map<String, Integer> totalPrices = new HashMap<String, Integer>();
        Map<String, Integer> counts = new HashMap<String, Integer>();

        for (Publication pub : publications) {

            String type = getPublicationType(pub);

            totalPrices.put(
                    type,
                    totalPrices.getOrDefault(type, 0)
                    + pub.getPrice()
            );

            counts.put(
                    type,
                    counts.getOrDefault(type, 0) + 1
            );
        }

        Map<String, Double> averages =
                new HashMap<String, Double>();

        for (String type : totalPrices.keySet()) {

            averages.put(
                    type,
                    (double) totalPrices.get(type)
                    / counts.get(type)
            );
        }

        return averages;
    }


    public Map<String, Double> calculatePublicationDistribution(
            Publication[] publications) {

        Map<String, Integer> counts =
                new HashMap<String, Integer>();

        for (Publication pub : publications) {

            String type = getPublicationType(pub);

            counts.put(
                    type,
                    counts.getOrDefault(type, 0) + 1
            );
        }

        Map<String, Double> distribution =
                new HashMap<String, Double>();

        for (String type : counts.keySet()) {

            double percentage =
                    (double) counts.get(type)
                    / publications.length * 100;

            distribution.put(type, percentage);
        }

        return distribution;
    }


    public double calculatePublicationRatioByYear(
            Publication[] publications,
            String year) {

        int count = 0;

        for (Publication pub : publications) {

            if (pub.getPublishDate().substring(0, 4).equals(year)) {
                count++;
            }
        }

        return (double) count / publications.length * 100;
    }


    private String getPublicationType(Publication pub) {

        if (pub instanceof Novel) {
            return "소설";

        } else if (pub instanceof Magazine) {
            return "잡지";

        } else if (pub instanceof ReferenceBook) {
            return "참고서";

        } else {
            return "기타";
        }
    }


    public void printStatistics(Publication[] publications) {

        DecimalFormat df = new DecimalFormat("#,###.##");

        System.out.println("===== 출판물 통계 분석 =====");

        Map<String, Double> averages =
                calculateAveragePriceByType(publications);

        System.out.println("1. 타입별 평균 가격:");

        for (String type : averages.keySet()) {
            System.out.println(
                    "- " + type + ": "
                    + df.format(averages.get(type))
                    + "원"
            );
        }


        Map<String, Double> distribution =
                calculatePublicationDistribution(publications);

        System.out.println("2. 출판물 유형 분포:");

        for (String type : distribution.keySet()) {
            System.out.println(
                    "- " + type + ": "
                    + df.format(distribution.get(type))
                    + "%"
            );
        }


        double ratio =
                calculatePublicationRatioByYear(
                        publications, "2007"
                );

        System.out.println(
                "3. 2007년에 출판된 출판물 비율: "
                + df.format(ratio)
                + "%"
        );
    }
}