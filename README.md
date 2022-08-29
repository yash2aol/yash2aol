### Hello

This project is to showcase the abilities of Stanford CoreNLP.

There are two APIs
1. /run - To upload a file to a repository.
2. /getFile/{fileCode} - To retrieve any file from repo and perform the text manipulation and basic mining tasks.

The code reads the file text.txt and store the the output in output.txt. For now both the files are present in resources folder.

The project defines 4 basic mining tasks:
1. To count the number of sentences:
    Output:
      Total Sentences: 3
      
2. To count the number of words:
    Output
      Total words in the document: 67
      
3. To count all the nouns and their occurences
    Output
      Nouns and their counts: {Opin=1, Rank=1, Review=1, Tripadvisor=1, Edmunds=1}
      
4. Random statistics: to figure out the document sentiment
    Output
      Sentiment Score: 3.0
      Sentiment Type: Positive
      Very positive: 29.0%
      Positive: 50.0%
      Neutral: 14.0%
      Negative: 6.0%
      Very negative: 1.0%
      
How to Run:
1. Add the desired text to the text.txt file
2. Run the file TextFIlterNew.java as a java file.
3. After the process finishes, the output is visible in the output.txt file.
