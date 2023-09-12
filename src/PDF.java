import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;

public class PDF {

	private ResultSet result;

	public PDF(ResultSet resultSet) {
		result = resultSet;

	}

	public void text(String fileName) {

		Document document = new Document();

		try {

			PdfWriter.getInstance(document, new FileOutputStream(new File("invoice/" + fileName + ".pdf")));

			document.open();

			// set Font for the title
			Font f = new Font();
			f.setStyle(Font.BOLD);
			f.setSize(16);

			Paragraph title1 = new Paragraph("INVOICE", f);
			Paragraph title2 = new Paragraph("Summay", f);
			Paragraph title3 = new Paragraph("Last month", f);
			title1.setAlignment(Element.ALIGN_CENTER);
			try {
				// set font size for the content
				f.setSize(12);
				Paragraph information = new Paragraph();
				Paragraph information1 = new Paragraph();
				Paragraph information2 = new Paragraph();
				Paragraph information3 = new Paragraph();
				Paragraph information4 = new Paragraph(" ");
				
				// add company and customer information
				information.add("Green Energy Company" + "                                            "
						+ "Account number: " + String.valueOf(result.getInt("customerID")));
				information1.add("retailenergyprovider.com" + "                                           "
						+ result.getString("customerName"));
				information2.add("customercare@retailenergyprovider.com" + "                 "
						+ result.getString("customerAddress"));
				information3.add("1-800-129-456" + "                                                           "
						+ result.getString("phoneNumber"));

				information.setAlignment(Element.ALIGN_LEFT);
				information1.setAlignment(Element.ALIGN_LEFT);
				information2.setAlignment(Element.ALIGN_LEFT);
				information3.setAlignment(Element.ALIGN_LEFT);

				Paragraph summary = new Paragraph();
				Paragraph summary0 = new Paragraph("  ");
				Paragraph summary1 = new Paragraph();
				Paragraph summary2 = new Paragraph();
				Paragraph summary3 = new Paragraph();
				Paragraph summary4 = new Paragraph();
				Paragraph summary5 = new Paragraph();
				Paragraph summary6 = new Paragraph();
				
				//add the summary for this month
				summary1.add("Total use this month(Kwh):................................"
						+ String.valueOf(result.getDouble("totalUsage")));
				summary2.add("Cost (0.16/Kwh):................................................"
						+ String.valueOf(result.getDouble("cost")));
				summary3.add("City tax (6.5%):..................................................."
						+ String.valueOf(result.getDouble("tax")));
				summary4.add("Remaining:........................................................."
						+ String.valueOf(result.getDouble("remaining")));
				summary5.add("Amount due:....................................................." + "$ "
						+ String.valueOf(result.getDouble("amountDue")));
				summary6.add("Due date:.........................................................."
						+ result.getString("dueDate"));
				summary.add(
						"                                                                        " + "----------- ");

				Paragraph lastMonth = new Paragraph();
				Paragraph lastMonth1 = new Paragraph();
				Paragraph lastMonth2 = new Paragraph();
				Paragraph lastMonth3 = new Paragraph();
				Paragraph lastMonth4 = new Paragraph();
				
				//add the summary for last month
				lastMonth.add("Usage:......................................."
						+ String.valueOf(result.getDouble("lastUsage")));
				lastMonth1.add("Cost:.........................................."
						+ String.valueOf(result.getDouble("lastCost")));
				lastMonth2.add("Payment:..................................."
						+ String.valueOf(result.getDouble("lastPayment")));
				lastMonth3.add("Remaining:................................"
						+ String.valueOf(result.getDouble("lastRemaining")));
				lastMonth4.add("Due date:..............................." + result.getString("lastDueDate"));

				document.add(title1);
				document.add(information4);
				document.add(information);
				document.add(information1);
				document.add(information2);
				document.add(information3);
				document.add(summary0);
				document.add(title2);
				document.add(summary1);
				document.add(summary2);
				document.add(summary3);
				document.add(summary4);
				document.add(summary);
				document.add(summary5);
				document.add(summary6);
				document.add(summary0);
				document.add(title3);
				document.add(lastMonth);
				document.add(lastMonth1);
				document.add(lastMonth2);
				document.add(lastMonth3);
				document.add(lastMonth4);

			} catch (Exception E) {
			}

			document.close();
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}

	}

}
