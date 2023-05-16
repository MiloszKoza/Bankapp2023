package com.company;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PDF {

    public void generateConfirmationPDF (Transaction transaction, int accountNumber, String holderFirstName, String holderLastName){

         String AlphanumericString = "ABCDEFGHIJKLMNOPQRSTVWXYZ" + "0123456789";

         StringBuilder  sb = new StringBuilder(6);
         for(int i=0; i <6; i++){
             int index = (int) (AlphanumericString.length()*Math.random());
             sb.append(AlphanumericString.charAt(index));

         }

        String convertedSb = sb.toString();

        String file = "C:\\Users\\milos\\Documents\\PDFs\\"+accountNumber+"\\Potwierdzenie_Operacji_" + convertedSb+ ".pdf";

        Document document = new Document();
        LocalDate localDate = LocalDate.now();

        try {
            PdfWriter.getInstance(document,new FileOutputStream(file));
            document.open();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(bf,12);

            Image image1 = Image.getInstance("C:\\Users\\milos\\Downloads\\290147_bank_cash_finance_money_payment_icon (1).png");
            document.add(image1);

            Paragraph para = new Paragraph("Właściciel: " + holderFirstName + " " + holderLastName, font );
            document.add(para);
            document.add(new Paragraph("Data wydruku: " + localDate, font));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Numer rachunku: " + accountNumber, font));
            document.add(new Paragraph("Data operacji: " + transaction.getDate(), font));
            if (transaction.isBeingRecipient() == true) {
                document.add(new Paragraph("Nadawca: " + transaction.getRecipientSenderFirstName() + " " + transaction.getRecipientSenderLastName(), font) );
                document.add(new Paragraph("Rachunek nadawcy: " + transaction.getAccountNumber(), font));
            }else{
                document.add(new Paragraph("Odbiorca: " + transaction.getRecipientSenderFirstName() + " " + transaction.getRecipientSenderLastName(), font));
                document.add(new Paragraph("Rachunek odbiorcy: " + transaction.getAccountNumber(), font));


            }
            document.add(new Paragraph("Tytuł: " + transaction.getTitle(),font));
            document.add(new Paragraph("Kwota: " + transaction.getAmount(),font));

            document.close();



        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
