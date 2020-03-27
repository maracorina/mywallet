package wllt.utils;

import org.springframework.beans.factory.annotation.Autowired;
import wllt.dao.TransactionDao;
import wllt.entities.types.CategoryType;

import java.io.*;

public class FileUtils {

    @Autowired
    public TransactionDao transactionDao;

    public void loadTransactions() throws IOException {
        String row;
        String sql = "";
        BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Corina\\An III\\Licenta\\wallet\\transactions.csv"));
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            Integer userId = Integer.parseInt(data[0]);
            CategoryType type = CategoryType.get(data[1]);
            String category = data[2];
            String date = data[4];
            Integer sum = Integer.parseInt(data[3]);

            String sqlQuery = "INSERT INTO `transactions`(`user_id`, `sum`, `type`, `category`, `date`) VALUES (" +
                    userId + "," +
                    sum + ",'" +
                    type + "','" +
                    category + "','" +
                    date + "');";

            sql = sql + sqlQuery + "\n";
        }
        csvReader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Corina\\An III\\Licenta\\wallet\\query.txt"));
        writer.write(sql);

        writer.close();
    }
}
