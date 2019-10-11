package com.cjremshaw.statsutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class StatisticsUtil {
    public static OptionalDouble mean(int... values ) {
        return Arrays.stream(values).average();
    }

    public static OptionalDouble median(int... values ) {
        long size = values.length;
        return Arrays.stream(values).sorted().skip((size-1)/2).limit(2-(size%2)).average();
    }

    // mode can be multimodal
    // no mode if all numbers appear the same number of times
    public static List<Integer> mode(int ... values ) {
        Map<Integer,Long> grouped =
                Arrays.stream(values).boxed().collect(Collectors.groupingBy( Function.identity(),
                        Collectors.counting() ));
        ArrayList<Map.Entry<Integer,Long>> maxFreqs =
                grouped.entrySet().stream().sorted(Comparator.comparingLong(Map.Entry::getValue)).collect(
                Collector.of(
                        ArrayList::new,
                        (result,entry) -> {
                            if ( result.size() == 0 || result.get(0).getValue().equals(entry.getValue())) {
                                result.add(entry);
                            } else if ( result.get(0).getValue() < entry.getValue() ) {
                                result.clear();
                                result.add( entry );
                            }
                        },
                        (result1,result2) -> {
                            int comp =
                                    Long.compare(result1.get(0).getValue(), result2.get(0).getValue());
                            if (comp == 0) {
                                result1.addAll(result2);
                                return result1;
                            } else if (comp == 1) {
                                return result1;
                            } else {
                                return result2;
                            }
                        }
                ));
        if ( grouped.size() == maxFreqs.size() ) {
            return new ArrayList<>();
        }
        return maxFreqs.stream().map( Map.Entry::getKey ).collect( Collectors.toList() );
    }
}
