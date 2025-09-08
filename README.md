To start the app you need 1.compile and 2. run the app  
1. to compile run next command in commandline in appDirectory  
javac -d bin -sourcepath src\main\java src\main\java\by\samtsov\Main.java  
2. to run the app use next command in the same order  
java -cp bin by.samtsov.Main  
or:  
java -cp bin by.samtsov.Main --sort=name --order=asc --stat  
java -cp bin by.samtsov.Main -s=salary --order=desc --stat -o=file --path=output/statistics.txt  
java -cp bin by.samtsov.Main --stat  
