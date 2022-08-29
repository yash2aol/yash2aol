package org.uarizona.dto;

public class SentimentResult {

    double sentimentScore;
    String sentimentType;
    SentimentClassification sentimentClassification;

    public double getSentimentScore() {
        return sentimentScore;
    }

    public void setSentimentScore(double sentimentScore) {
        this.sentimentScore = sentimentScore;
    }

    public String getSentimentType() {
        return sentimentType;
    }

    public void setSentimentType(String sentimentType) {
        this.sentimentType = sentimentType;
    }

    public SentimentClassification getSentimentClassification() {
        return sentimentClassification;
    }

    public void setSentimentClassification(SentimentClassification sentimentClassification) {
        this.sentimentClassification = sentimentClassification;
    }

    @Override
    public String toString() {
        return "SentimentResult{" + '\n' +
                "sentimentScore= " + sentimentScore + '\n' +
                "sentimentType= '" + sentimentType + '\'' + '\n' +
                '}';
    }
}