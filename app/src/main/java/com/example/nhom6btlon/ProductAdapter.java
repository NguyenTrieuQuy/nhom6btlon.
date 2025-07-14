package com.example.nhom6btlon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    List<Product> list;
    List<Product> originalList;
    Context context;
    DatabaseHelper db;
    boolean showDeleteButton;
    boolean showAddToCartButton;

    public ProductAdapter(Context context, List<Product> list, DatabaseHelper db, boolean showDeleteButton, boolean showAddToCartButton) {
        this.context = context;
        this.list = new ArrayList<>(list);
        this.originalList = new ArrayList<>(list);
        this.db = db;
        this.showDeleteButton = showDeleteButton;
        this.showAddToCartButton = showAddToCartButton;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name, price;
        Button btnDelete, btnAddToCart;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgProduct);
            name = itemView.findViewById(R.id.txtName);
            price = itemView.findViewById(R.id.txtPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int i) {
        Product p = list.get(i);
        h.name.setText(p.name);
        h.price.setText(p.price);
        h.img.setImageResource(p.image);

        h.btnDelete.setVisibility(showDeleteButton ? View.VISIBLE : View.GONE);
        h.btnAddToCart.setVisibility(showAddToCartButton ? View.VISIBLE : View.GONE);

        h.btnDelete.setOnClickListener(v -> {
            db.deleteProduct(p.id);
            list.remove(i);
            originalList.remove(p);
            notifyItemRemoved(i);
            Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
        });

        h.btnAddToCart.setOnClickListener(v -> {
            boolean success = db.addToCart(p);
            if (success) {
                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Thêm vào giỏ hàng thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }











    // Hàm bỏ dấu tiếng Việt
    //Hàm này dùng để chuyển một chuỗi tiếng Việt có dấu thành chuỗi không dấu.
    public static String removeAccents(String s) {
        if (s == null) return "";  //Nếu chuỗi truyền vào là null, trả về chuỗi rỗng "".
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD); //Sẽ tách các ký tự có dấu thành ký tự cơ bản + ký tự dấu. vd:á -->a
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");//Nhận diện tất cả các ký tự dấu.
        return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d");
    } //Loại bỏ hết các dấu vừa nhận diện, chỉ giữ lại ký tự không dấu. //Vì ký tự Đ/đ không phải là dấu nên cần đổi riêng.

    // Hàm filter sản phẩm
    public void filter(String keyword) {
        list.clear();//xóa sạch danh sách hiện tại (list) để cập nhật danh sách mới



        if (keyword == null || keyword.trim().isEmpty()) {
            list.addAll(originalList); //nếu từ khóa rỗng hoặc null người dùng chưa nhập gì hoặc xóa hết chữ -->hiển thị toàn bộ danh sách gốc (originallist)
        }//chuẩn hóa từ khóa: -trim: loại bỏ khoảng trắng đầu cuối.



        else {
            String keywordNoAccent = removeAccents(keyword.trim().toLowerCase());
            for (Product p : originalList) {  //chuẩn hóa từ khóa: -trim: loại bỏ khoảng trắng đầu cuối.
                //- tolowerCase: chuyển hết thành chữ thường
                //- removeAccents: Chuyển đổi bỏ dấu tiếng việt vd: lọc thành loc giúp tìm ko dấu


                //Đây là đoạn code lọc sản phẩm theo từ khoá không dấu trong hàm filter, mình sẽ giải thích kỹ từng dòng:
                //mục đích: dễ dàng so sánh  với từ khóa tìm kiếm cũng đã được chuẩn hóa tương tự
                String nameNoAccent = removeAccents(p.getName().toLowerCase()); // - p.getName: lấy tên sản phẩm hiện tại //removeaccents: chuyển đổi bỏ dấu tiếng việt giúp tìm ko dấu  //tolowercase: chuyển hết về chữ thường
                if (nameNoAccent.contains(keywordNoAccent)) {// kết quả được lưu trữ bằng biến namenoaccent
                    list.add(p);// nếu điều kiện ở trên đúng thì sẽ hiển thị kết quả
                }
            }
        }
        notifyDataSetChanged(); //sau khi lọc xong sẽ gọi hàm này để thay đổi, làm mới dữ liệu lại  -- nếu không có dòng này sẽ ko làm mới lại dữ liệu mặc dù đã đổi.
    }
}
