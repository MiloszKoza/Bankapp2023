package com.company;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PDF {

    public void generateConfirmationPDF (Transaction transaction, int accountNumber, String holderFirstName, String holderLastName){
        String file = "C:\\Users\\milos\\Documents\\PDFs\\test1.pdf";

        Document document = new Document();
        LocalDate localDate = LocalDate.now();

        try {
            PdfWriter.getInstance(document,new FileOutputStream(file));
            document.open();

            Paragraph para = new Paragraph("Wlaściciel: " + holderFirstName + "  " + holderLastName );
            document.add(para);
            document.add(new Paragraph("Data Wydruku: " + localDate));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Numer rachunku: " + accountNumber));
            document.add(new Paragraph("Data operacji: " + transaction.getDate()));
            if (transaction.isBeingRecipient() == true) {
                document.add(new Paragraph("Nadawca:" + transaction.getRecipientSenderFirstName() + " " + transaction.getRecipientSenderLastName()));
                document.add(new Paragraph("Rachunek Nadawcy: " + transaction.getAccountNumber()));
            }else{
                document.add(new Paragraph("Odbiorca: " + transaction.getRecipientSenderFirstName() + " " + transaction.getRecipientSenderLastName()));
                document.add(new Paragraph("Rachunek odbiorcy: " + transaction.getAccountNumber()));


            }
            document.add(new Paragraph("Tytul: " + transaction.getTitle()));
            document.add(new Paragraph("Kwota: " + transaction.getAmount()));

            document.close();


        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }





    }



    public static void main(String[] args) {


//        String file =  "C:\\Users\\Miłosz\\Documents\\PDFs\\test.pdf";
//
//        Document document = new Document();
//
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream(file));
//
//            document.open();
//
//            Paragraph para = new Paragraph("This is testing from MiloszKoza");
//            document.add(para);
//            document.close();
//
//            System.out.println("Finish successfully");
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

}
