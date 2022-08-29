package org.uarizona.dto;

import java.util.Map;

public class TextFilterResult {

    long totalSentences;
    long totalWords;
    Map<String, Integer> nounCount;
    SentimentResult sentimentResult;

    public long getTotalSentences() {
        return totalSentences;
    }

    public void setTotalSentences(long totalSentences) {
        this.totalSentences = totalSentences;
    }

    public long getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(long totalWords) {
        this.totalWords = totalWords;
    }

    public Map<String, Integer> getNounCount() {
        return nounCount;
    }

    public void setNounCount(Map<String, Integer> nounCount) {
        this.nounCount = nounCount;
    }

    public SentimentResult getSentimentResult() {
        return sentimentResult;
    }

    public void setSentimentResult(SentimentResult sentimentResult) {
        this.sentimentResult = sentimentResult;
    }

    @Override
    public String toString() {
        return "TextFilterResult{" + '\n' +
                "totalSentences=" + totalSentences + '\n' +
                ", totalWords=" + totalWords + '\n' +
                ", nounCount=" + nounCount + '\n' +
                ", sentimentResult=" + sentimentResult.toString() + '\n' +
                '}';
    }
}
