IDENTIFICATION DIVISION.
   PROGRAM-ID. SAMPLE1.
   DATA DIVISION.
   WORKING-STORAGE SECTION.
   01 VAR1 PIC 9(4).
   01 VAR2 PIC X(10).
   PROCEDURE DIVISION.
       DISPLAY "Enter a number:".
       ACCEPT VAR1.
       IF VAR1 > 100 THEN
           DISPLAY "Number is greater than 100."
       ELSE
           DISPLAY "Number is 100 or less.".
       STOP RUN.