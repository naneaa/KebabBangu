package com.elaine.kebabbangu.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.elaine.kebabbangu.R;
import com.elaine.kebabbangu.base.Product;
import com.elaine.kebabbangu.dao.ProductDAO;

public class NewProductActivity extends AppCompatActivity {

    private EditText textName;
    private EditText textPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        textName = (EditText) findViewById(R.id.textName);
        textPrice = (EditText) findViewById(R.id.textPrice);
    }

    public void callCreateProduct(View view){

        Product product = new Product();
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
                    "Novo aluno " + product.getName() + " salvo!",
                    Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(NewProductActivity.this,
                    "Erro ao salvar novo aluno. \n" + e.getMessage(),
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

        Product product = new Product(name, Double.valueOf(price));

        return product;
    }
}
