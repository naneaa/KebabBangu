package com.elaine.kebabbangu.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.dao.ProductDAO;

public class NewProductActivity extends AppCompatActivity {

    private EditText textName;
    private EditText textPrice;
    private CheckBox hasSauce, hasSalad, hasCheese, onMenu;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        textName = (EditText) findViewById(R.id.textName);
        textPrice = (EditText) findViewById(R.id.textPrice);
        hasSalad = (CheckBox) findViewById(R.id.checkHasSalad);
        hasSauce = (CheckBox) findViewById(R.id.checkHasSauce);
        hasCheese = (CheckBox) findViewById(R.id.checkHasCheese);
        onMenu = (CheckBox) findViewById(R.id.checkOnMenu);

        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra("product");
        if (product != null) {
            textName.setText(product.getName());
            textPrice.setText(Double.toString(product.getPrice()));
            hasSalad.setChecked(product.hasSalad());
            hasSauce.setChecked(product.hasSauce());
            hasCheese.setChecked(product.hasCheese());
            onMenu.setChecked(product.isOnMenu());
        } else {
            product = new Product();
        }
    }

    public void callCreateProduct(View view) {
        Product product;
        try {
            ProductDAO productDAO = new ProductDAO(NewProductActivity.this);
            product = buildProductForInsert();

            if (product.getId() != null) {
                productDAO.update(product);
            } else {
                productDAO.create(product);
            }

            productDAO.close();
            Toast.makeText(NewProductActivity.this,
                    "Produto " + product.getName() + " salvo!",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(NewProductActivity.this,
                    "Erro ao salvar novo produto. \n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private Product buildProductForInsert() throws Exception {

        if (textName.getText().toString().equals("")) {
            throw new Exception("Campo 'nome' obrigatório!");
        }

        if (textPrice.getText().toString().equals("")) {
            throw new Exception("Campo 'preço' obrigatório!");
        }

        String name = textName.getText().toString();

        String price = textPrice.getText().toString();

        product.setName(name);
        product.setPrice(Double.valueOf(price));
        product.setHasSauce(hasSauce.isChecked());
        product.setHasSalad(hasSalad.isChecked());
        product.setHasCheese(hasCheese.isChecked());
        product.setOnMenu(onMenu.isChecked());

        return product;
    }
}
