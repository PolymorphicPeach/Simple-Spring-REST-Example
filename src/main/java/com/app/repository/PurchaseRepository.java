package com.app.repository;

import com.app.model.Purchase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PurchaseRepository {
    private final JdbcTemplate jdbc;

    // Constructor injection
    public PurchaseRepository(JdbcTemplate jdbc){
        this.jdbc = jdbc;
    }

    public void storePurchase(Purchase purchase){

        // First value is NULL because the database was configured to generate the value
        // ?s will be replaced by purchase.getProduct() and purchase.getPrice()
        String sql = "INSERT INTO purchase (product, price) VALUES (?, ?)";

        jdbc.update(sql, purchase.getProduct(), purchase.getPrice());
    }

    public List<Purchase> getAllPurchases(){
        String sql = "SELECT * FROM purchase";

        // r = ResultSet = Data retrieved from the database
        // i = int = The row number
        RowMapper<Purchase> purchaseRowMapper = (r, i) ->{
            Purchase rowObject = new Purchase();
            rowObject.setId(r.getInt("id"));
            rowObject.setProduct(r.getString("product"));
            rowObject.setPrice(r.getBigDecimal("price"));
            return rowObject;
        };

        // Sends the SELECT query on the RowMapper
        return jdbc.query(sql, purchaseRowMapper);
    }
}
