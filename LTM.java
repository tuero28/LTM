TCP Object Stream — 6 Bài Đầy Đủ
Lập trình mạng INT1433 · B23DCCN926 · Bùi Quang Vinh
✅ mM5m0V4s  ✅ fL6WEKh7  •  XtWjagNp  •  W7S23nSu  •  151GNZvT  •  j5ELZdmS
FORM CHUẨN TCP OBJECT STREAM
Cấu trúc thư mục NetBeans (BẮT BUỘC)
Source Packages/
├── TCP/                ← package tên TCP (chữ hoa, khớp server)
│   ├── SomeClass.java  ← implements Serializable
│   └── Main.java       ← cùng package TCP

⚠ ObjectOutputStream PHẢI khởi tạo TRƯỚC ObjectInputStream — tránh deadlock
⚠ Package PHẢI là TCP (chữ hoa) — server deserialize theo tên TCP.ClassName
⚠ Gửi mã SV bằng writeObject() — KHÔNG dùng writeUTF()
⚠ setSoTimeout(5000) bắt buộc · flush() sau writeObject() · đóng: in.close(); out.close(); socket.close()
Skeleton Main.java
package TCP;
import java.util.*; import java.io.*; import java.net.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);
        socket.setSoTimeout(5000);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); // TRƯỚC
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());     // SAU
        out.writeObject("B23DCCN926;qCode");
        out.flush();
        SomeClass obj = (SomeClass) in.readObject();
        // xử lý obj...
        out.writeObject(obj);
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

BÀI 1 — mM5m0V4s ✅ AC · Laptop (TCP.Laptop)
Đề bài
Mã câu hỏi: mM5m0V4s · Cổng: 2209

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng máy tính xách tay và thay đổi thông tin. Cụ thể:

a. Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau:
   • Tên đầy đủ của lớp: TCP.Laptop
   • Các thuộc tính: id int, code String, name String, quantity int
   • Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
   • Trường dữ liệu: private static final long serialVersionUID = 20150711L;

b. Tương tác với server theo kịch bản dưới đây:
   1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;F2DA54F3"
   2) Nhận một đối tượng là thể hiện của lớp Laptop từ server với các thông tin đã được thiết lập
   3) Thay đổi thông tin theo các yêu cầu dưới đây và gán vào các thuộc tính tương ứng:
      - Đảo vị trí từ đầu tiên và từ cuối cùng trong thuộc tính name. Ví dụ: Laptop Acer Predator Helios → Helios Acer Predator Laptop
      - Đảo ngược các chữ số trong thuộc tính quantity. Ví dụ: 358 → 853
      Gửi đối tượng đã được sửa đổi lên server.
   4) Đóng socket và kết thúc chương trình.
Code
TCP/Laptop.java
package TCP;
import java.io.Serializable;
public class Laptop implements Serializable {
    private static final long serialVersionUID = 20150711L;
    private int id;
    private String code, name;
    private int quantity;
    public Laptop(int id, String code, String name, int quantity) {
        this.id = id; this.code = code; this.name = name; this.quantity = quantity;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
TCP/Main.java
package TCP;
import java.util.*; import java.io.*; import java.net.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);
        socket.setSoTimeout(5000);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.writeObject("B23DCCN926;mM5m0V4s");
        out.flush();
        Laptop lp = (Laptop) in.readObject();

        // Đảo từ đầu và từ cuối trong name
        String[] words = lp.getName().split(" ");
        String tmp = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = tmp;
        lp.setName(String.join(" ", words));

        // Đảo ngược chữ số trong quantity
        lp.setQuantity(Integer.parseInt(
            new StringBuilder(String.valueOf(lp.getQuantity())).reverse().toString()));

        out.writeObject(lp);
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

BÀI 2 — fL6WEKh7 ✅ AC · Khách hàng (TCP.Customer)
Đề bài
Mã câu hỏi: fL6WEKh7 · Cổng: 2209

Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
a. Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong → DUONG, Nguyen Van Hai
b. Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 → 11/10/2012
c. Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong → nvhduong

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:

a. Đối tượng trao đổi là thể hiện của lớp Customer được mô tả như sau:
   • Tên đầy đủ của lớp: TCP.Customer
   • Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String
   • Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
   • Trường dữ liệu: private static final long serialVersionUID = 20170711L;

b. Tương tác với server theo kịch bản dưới đây:
   1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;F2DA54F3"
   2) Nhận một đối tượng là thể hiện của lớp Customer từ server với các thông tin đã được thiết lập
   3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng. Gửi đối tượng đã được sửa đổi lên server.
   4) Đóng socket và kết thúc chương trình.
Code
TCP/Customer.java
package TCP;
import java.io.Serializable;
public class Customer implements Serializable {
    private static final long serialVersionUID = 20170711L;
    private int id;
    private String code, name, dayOfBirth, userName;
    public Customer(int id, String code, String name, String dayOfBirth, String userName) {
        this.id = id; this.code = code; this.name = name;
        this.dayOfBirth = dayOfBirth; this.userName = userName;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDayOfBirth() { return dayOfBirth; }
    public void setDayOfBirth(String dayOfBirth) { this.dayOfBirth = dayOfBirth; }
    public void setUserName(String userName) { this.userName = userName; }
}
TCP/Main.java
package TCP;
import java.util.*; import java.io.*; import java.net.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);
        socket.setSoTimeout(5000);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.writeObject("B23DCCN926;fL6WEKh7");
        out.flush();
        Customer c = (Customer) in.readObject();
        String[] words = c.getName().split(" ");

        // a. Tên: từ cuối ALL_CAPS, các từ còn lại Capitalize (lowercase trước)
        String lastName = words[words.length - 1].toUpperCase();
        StringBuilder rest = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            if (i > 0) rest.append(" ");
            String w = words[i].toLowerCase();
            rest.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1));
        }
        c.setName(lastName + ", " + rest.toString());

        // b. Ngày sinh: mm-dd-yyyy → dd/mm/yyyy
        String[] parts = c.getDayOfBirth().split("-");
        c.setDayOfBirth(parts[1] + "/" + parts[0] + "/" + parts[2]);

        // c. userName: chữ đầu mỗi từ (trừ từ cuối) + từ cuối, lowercase
        StringBuilder userName = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) userName.append(words[i].charAt(0));
        userName.append(words[words.length - 1]);
        c.setUserName(userName.toString().toLowerCase());

        out.writeObject(c);
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

BÀI 3 — XtWjagNp · Địa chỉ khách hàng (TCP.Address)
Đề bài
Mã câu hỏi: XtWjagNp · Cổng: 2209

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) để gửi/nhận và chuẩn hóa thông tin địa chỉ của khách hàng.

Biết rằng lớp TCP.Address có các thuộc tính (id int, code String, addressLine String, city String, postalCode String) và trường dữ liệu private static final long serialVersionUID = 20180801L.

a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;A1B2C3D4"

b. Nhận một đối tượng là thể hiện của lớp TCP.Address từ server. Thực hiện chuẩn hóa thông tin addressLine bằng cách:
   • Chuẩn hóa addressLine: Viết hoa chữ cái đầu mỗi từ, in thường các chữ còn lại, loại bỏ ký tự đặc biệt và khoảng trắng thừa (ví dụ: "123 nguyen!!! van cu" → "123 Nguyen Van Cu")
   • Chuẩn hóa postalCode: Chỉ giữ lại số và ký tự "-" ví dụ: "123-456"

c. Gửi đối tượng đã được chuẩn hóa thông tin địa chỉ lên server.

d. Đóng kết nối và kết thúc chương trình.
Code
TCP/Address.java
package TCP;
import java.io.Serializable;
public class Address implements Serializable {
    private static final long serialVersionUID = 20180801L;
    private int id;
    private String code, addressLine, city, postalCode;
    public String getAddressLine() { return addressLine; }
    public void setAddressLine(String addressLine) { this.addressLine = addressLine; }
    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
}
TCP/Main.java
package TCP;
import java.util.*; import java.io.*; import java.net.*;
public class Main {
    static String chuanHoaWord(String s) {
        StringBuilder res = new StringBuilder();
        for (char c : s.toCharArray())
            if (Character.isLetterOrDigit(c)) res.append(c);
        String clean = res.toString().toLowerCase();
        if (clean.isEmpty()) return "";
        return Character.toUpperCase(clean.charAt(0)) + clean.substring(1);
    }
    static String chuanHoaAddress(String s) {
        String[] parts = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(chuanHoaWord(parts[i]));
        }
        return sb.toString();
    }
    static String chuanHoaPostal(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray())
            if (Character.isDigit(c) || c == '-') sb.append(c);
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);
        socket.setSoTimeout(5000);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.writeObject("B23DCCN926;XtWjagNp");
        out.flush();
        Address ad = (Address) in.readObject();
        ad.setAddressLine(chuanHoaAddress(ad.getAddressLine()));
        ad.setPostalCode(chuanHoaPostal(ad.getPostalCode()));
        out.writeObject(ad);
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

BÀI 4 — W7S23nSu · Sản phẩm Laptop v2 (TCP.Laptop)
Đề bài (dịch ngược từ code)
Mã câu hỏi: W7S23nSu · Cổng: 2209

Thông tin sản phẩm laptop vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
a. Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng. Ví dụ: "lenovo thinkpad T520" bị chuyển thành "T520 thinkpad lenovo"
b. Số lượng sản phẩm cũng bị đảo ngược các chữ số. Ví dụ: từ 358 thành 853

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. Chi tiết dưới đây:

a. Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau:
   • Tên đầy đủ của lớp: TCP.Laptop
   • Các thuộc tính: id int, code String, name String, quantity int
   • Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên
   • Trường dữ liệu: private static final long serialVersionUID = 20150711L;

b. Tương tác với server theo kịch bản:
   1) Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode"
   2) Nhận một đối tượng là thể hiện của lớp TCP.Laptop từ server
   3) Sửa lại tên (đảo vị trí từ đầu và từ cuối) và số lượng (đảo ngược chữ số). Gửi đối tượng đã sửa lên server
   4) Đóng socket và kết thúc chương trình

⚠ Đề dịch ngược từ code SanPham.java — logic giống bài 1, khác qCode và serialVersionUID
Code
TCP/Laptop.java — dùng lại class từ Bài 1, CHỈ đổi qCode trong Main
package TCP;
import java.util.*; import java.io.*; import java.net.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);
        socket.setSoTimeout(5000);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.writeObject("B23DCCN926;W7S23nSu");  // ← chỉ khác chỗ này
        out.flush();
        Laptop lp = (Laptop) in.readObject();
        String[] words = lp.getName().trim().split("\\s+");
        String tmp = words[0];
        words[0] = words[words.length - 1];
        words[words.length - 1] = tmp;
        lp.setName(String.join(" ", words));
        lp.setQuantity(Integer.parseInt(
            new StringBuilder(String.valueOf(lp.getQuantity())).reverse().toString()));
        out.writeObject(lp);
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

BÀI 5 — 151GNZvT · Sản phẩm giảm giá (TCP.Product)
Đề bài
Mã câu hỏi: 151GNZvT · Cổng: 2209

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây).

Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) theo kịch bản dưới đây:

Biết lớp TCP.Product gồm các thuộc tính (id int, name String, price double, discount int) và private static final long serialVersionUID = 20231107;

a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1E08CA31"

b. Nhận một đối tượng là thể hiện của lớp TCP.Product từ server.

c. Tính toán giá trị giảm giá theo price theo nguyên tắc: Giá trị giảm giá (discount) bằng tổng các chữ số trong phần nguyên của giá sản phẩm (price). Thực hiện gán giá trị cho thuộc tính discount và gửi đối tượng nhận được lên server.

d. Đóng kết nối và kết thúc chương trình.
Code
TCP/Product.java
package TCP;
import java.io.Serializable;
public class Product implements Serializable {
    private static final long serialVersionUID = 20231107;
    private int id, discount;
    private double price;
    private String name;
    public Product(int id, double price, String name) {
        this.id = id; this.price = price; this.name = name;
    }
    public double getPrice() { return price; }
    public void setDiscount(int discount) { this.discount = discount; }
}
TCP/Main.java
package TCP;
import java.util.*; import java.io.*; import java.net.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);
        socket.setSoTimeout(5000);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.writeObject("B23DCCN926;151GNZvT");
        out.flush();
        Product p = (Product) in.readObject();

        // discount = tổng các chữ số trong phần nguyên của price
        int intPart = (int) p.getPrice();
        int discount = 0;
        while (intPart > 0) {
            discount += intPart % 10;
            intPart /= 10;
        }
        p.setDiscount(discount);

        out.writeObject(p);
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

BÀI 6 — j5ELZdmS · Sinh viên GPA (TCP.Student)
Đề bài
Mã câu hỏi: j5ELZdmS · Cổng: 2209

Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s).

Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng (ObjectOutputStream/ObjectInputStream) theo kịch bản dưới đây:

Biết lớp TCP.Student gồm các thuộc tính (id int, code String, gpa float, gpaLetter String) và private static final long serialVersionUID = 20151107;

a. Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D059A3F"

b. Nhận một đối tượng là thể hiện của lớp TCP.Student từ server.

c. Chuyển đổi giá trị điểm số gpa của đối tượng nhận được sang dạng điểm chữ và gán cho gpaLetter.

   Nguyên tắc chuyển đổi:
   i.   3.7 – 4   → A
   ii.  3.0 – 3.7 → B
   iii. 2.0 – 3.0 → C
   iv.  1.0 – 2.0 → D
   v.   0   – 1.0 → F

d. Gửi đối tượng đã được xử lý ở trên lên server.

e. Đóng kết nối và kết thúc chương trình.
Code
TCP/Student.java
package TCP;
import java.io.Serializable;
public class Student implements Serializable {
    private static final long serialVersionUID = 20151107;
    private int id;
    private String code, gpaLetter;
    private float gpa;
    public Student(int id, String code, float gpa) {
        this.id = id; this.code = code; this.gpa = gpa;
    }
    public float getGpa() { return gpa; }
    public void setGpaLetter(String gpaLetter) { this.gpaLetter = gpaLetter; }
}
TCP/Main.java
package TCP;
import java.util.*; import java.io.*; import java.net.*;
public class Main {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2209);
        socket.setSoTimeout(5000);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        out.writeObject("B23DCCN926;j5ELZdmS");
        out.flush();
        Student st = (Student) in.readObject();

        float gpa = st.getGpa();
        if      (gpa >= 3.7f) st.setGpaLetter("A");
        else if (gpa >= 3.0f) st.setGpaLetter("B");
        else if (gpa >= 2.0f) st.setGpaLetter("C");
        else if (gpa >= 1.0f) st.setGpaLetter("D");
        else                  st.setGpaLetter("F");

        out.writeObject(st);
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

LƯU Ý THỰC CHIẾN
⚠ Run File (chuột phải Main.java → Run File) — KHÔNG Run Project
⚠ Tạo package TCP: chuột phải Source Packages → New → Java Package → đặt tên TCP
⚠ Class file (.java) và Main.java đều CÙNG package TCP
⚠ serialVersionUID phải đúng số trong đề — sai 1 chữ số là ClassNotFoundException
⚠ Bài Address: loại bỏ ký tự đặc biệt TRƯỚC khi viết hoa chữ đầu từ
⚠ Bài Product: (int) price lấy phần nguyên, cộng từng chữ số bằng % 10 / 10
⚠ Bài Student: gpa là float — so sánh dùng >= 3.7f (có hậu tố f)
⚠ Bài Customer: dùng words[] gốc (tên chưa format) để tạo userName
PHẦN 1 — TCP GZIP STREAM (Cổng 2210)

Import chuẩn:
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.zip.*;
import java.util.Base64;   // nếu đề yêu cầu Base64

Skeleton cố định (2 hàm dùng cho mọi bài GZIP):
    static void gzipSend(OutputStream rawOut, String text) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gz = new GZIPOutputStream(baos);
        gz.write(text.getBytes("UTF-8"));
        gz.close();
        rawOut.write(baos.toByteArray());
        rawOut.flush();
    }

    static String gzipRecv(InputStream rawIn) throws Exception {
        GZIPInputStream gzIn = new GZIPInputStream(rawIn);
        ByteArrayOutputStream decoded = new ByteArrayOutputStream();
        int b;
        while ((b = gzIn.read()) != -1) {
            decoded.write(b);
            if (b == '\n') break;
        }
        return decoded.toString("UTF-8").trim();
    }

Lưu ý bắt buộc:
- Đọc từng byte đến "\n" — KHÔNG dùng BufferedReader (gây EOFException)
- gzipSend dùng gz.close() không phải gz.finish()
- Base64 không padding: Base64.getEncoder().withoutPadding().encodeToString(...)

 Bài 1 — Đảo Ngược + Base64 | qCode: huPEuHGB | Cổng: 2210

Đề bài:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2210 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng GZIPInputStream/GZIPOutputStream để trao đổi thông tin (mỗi thông điệp là một dòng text UTF-8 kết thúc bằng '\n' và toàn bộ dữ liệu truyền/nhận đều được nén GZIP), theo thứ tự sau:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
   Ví dụ: B16DCCN999;GZLEN01
b. Nhận dữ liệu từ server, sau khi giải nén là một chuỗi văn bản.
c. Thực hiện đảo ngược chuỗi nhận được, sau đó mã hóa chuỗi đã đảo ngược sang định dạng Base64. Gửi kết quả lên server theo khuôn dạng: <reversed_string>|<base64_string>
   Ví dụ: Nhận "123" → Đảo ngược thành "321" → Base64 của "321" là "MzIx" → Gửi lên: 321|MzIx
d. Đóng kết nối và kết thúc chương trình.

Code:
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.zip.*;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2210);
        socket.setSoTimeout(5000);
        InputStream rawIn = socket.getInputStream();
        OutputStream rawOut = socket.getOutputStream();

        gzipSend(rawOut, "B23DCCN926;huPEuHGB\n");
        String s = gzipRecv(rawIn);

        // ===== LOGIC =====
        String reversed = new StringBuilder(s).reverse().toString();
        String b64 = Base64.getEncoder().withoutPadding()
                           .encodeToString(reversed.getBytes("UTF-8"));
        String kq = reversed + "|" + b64;
        // ==================

        gzipSend(rawOut, kq + "\n");
        socket.close();
    }

    static void gzipSend(OutputStream rawOut, String text) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gz = new GZIPOutputStream(baos);
        gz.write(text.getBytes("UTF-8"));
        gz.close();
        rawOut.write(baos.toByteArray());
        rawOut.flush();
    }

    static String gzipRecv(InputStream rawIn) throws Exception {
        GZIPInputStream gzIn = new GZIPInputStream(rawIn);
        ByteArrayOutputStream decoded = new ByteArrayOutputStream();
        int b;
        while ((b = gzIn.read()) != -1) {
            decoded.write(b);
            if (b == '\n') break;
        }
        return decoded.toString("UTF-8").trim();
    }
}

 Bài 2 — Sắp Xếp Ký Tự | qCode: vwyplwN8 | Cổng: 2210

Đề bài:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2210 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng GZIPInputStream/GZIPOutputStream để trao đổi thông tin (mỗi thông điệp là một dòng text UTF-8 kết thúc bằng '\n' và toàn bộ dữ liệu truyền/nhận đều được nén GZIP), theo thứ tự sau:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
   Ví dụ: B16DCCN999;GZCRC_LEN03
b. Nhận dữ liệu từ server, sau khi giải nén là một chuỗi văn bản.
c. Sắp xếp các ký tự trong chuỗi nhận được theo thứ tự từ điển (tăng dần theo mã ASCII). Sau đó gửi chuỗi kết quả đã sắp xếp lên server.
   Ví dụ: Nhận về "dbca1" thì gửi lên server "1abcd"
d. Đóng kết nối và kết thúc chương trình.

Code:
import java.util.*;
import java.io.*;
import java.net.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2210);
        socket.setSoTimeout(5000);
        InputStream rawIn = socket.getInputStream();
        OutputStream rawOut = socket.getOutputStream();

        gzipSend(rawOut, "B23DCCN926;vwyplwN8\n");
        String s = gzipRecv(rawIn);

        // ===== LOGIC =====
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        String kq = new String(arr);
        // ==================

        gzipSend(rawOut, kq + "\n");
        socket.close();
    }

    static void gzipSend(OutputStream rawOut, String text) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        GZIPOutputStream gz = new GZIPOutputStream(baos);
        gz.write(text.getBytes("UTF-8"));
        gz.close();
        rawOut.write(baos.toByteArray());
        rawOut.flush();
    }

    static String gzipRecv(InputStream rawIn) throws Exception {
        GZIPInputStream gzIn = new GZIPInputStream(rawIn);
        ByteArrayOutputStream decoded = new ByteArrayOutputStream();
        int b;
        while ((b = gzIn.read()) != -1) {
            decoded.write(b);
            if (b == '\n') break;
        }
        return decoded.toString("UTF-8").trim();
    }
}

 PHẦN 2 — TCP NIO (SocketChannel + Frame Protocol) (Cổng 2211)

Import chuẩn:
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

Skeleton cố định (2 hàm dùng cho mọi bài NIO):
    static void frameSend(SocketChannel sc, String text) throws Exception {
        byte[] payload = text.getBytes("UTF-8");
        ByteBuffer buf = ByteBuffer.allocate(4 + payload.length);
        buf.putInt(payload.length);
        buf.put(payload);
        buf.flip();
        while (buf.hasRemaining()) sc.write(buf);
    }

    static String frameRecv(SocketChannel sc) throws Exception {
        ByteBuffer lenBuf = ByteBuffer.allocate(4);
        while (lenBuf.hasRemaining()) sc.read(lenBuf);
        lenBuf.flip();
        int len = lenBuf.getInt();
        ByteBuffer payBuf = ByteBuffer.allocate(len);
        while (payBuf.hasRemaining()) sc.read(payBuf);
        payBuf.flip();
        return Charset.forName("UTF-8").decode(payBuf).toString();
    }

Lưu ý bắt buộc:
- Frame = 4 byte int (độ dài payload) + payload UTF-8
- while (buf.hasRemaining()) sc.read/write(buf) — readFully loop bắt buộc
- sc.configureBlocking(true) — dùng blocking mode cho đơn giản
- Không cần flush() — SocketChannel tự flush khi write()

 Bài 3 — Trích Xuất HTTP Request | qCode: u48syPvz | Cổng: 2211

Đề bài:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2211 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng SocketChannel và ByteBuffer để trao đổi thông tin theo giao thức frame: 4 byte độ dài (int32) + payload (UTF-8).
Lưu ý: server & client đều phải đọc đủ dữ liệu bằng vòng lặp (readFully) do server luôn chia nhỏ dữ liệu khi gửi. Trình tự trao đổi như sau:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
   Ví dụ: B16DCCN999;fkdRJYuX
b. Nhận dữ liệu từ server gồm đúng 3 frame liên tiếp. Payload của mỗi frame là một phần của cùng một HTTP request, client phải nối 3 payload theo đúng thứ tự để thu được chuỗi HTTP request hoàn chỉnh (các dòng phân tách bởi \r\n và kết thúc bằng \r\n\r\n).
c. Từ chuỗi HTTP request hoàn chỉnh, trích xuất và gửi lại lên server theo định dạng METHOD;PATH;HOST trong đó PATH luôn bao gồm query-string.
d. Đóng kết nối và kết thúc chương trình.

Code:
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class Main {
    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open(
            new InetSocketAddress("36.50.135.242", 2211));
        sc.configureBlocking(true);

        frameSend(sc, "B23DCCN926;u48syPvz");

        // ===== LOGIC =====
        String http = frameRecv(sc) + frameRecv(sc) + frameRecv(sc);
        String[] lines = http.split("\r\n");
        String[] requestLine = lines[0].split(" ");
        String method = requestLine[0];
        String path   = requestLine[1];
        String host   = "";
        for (String line : lines) {
            if (line.toLowerCase().startsWith("host:")) {
                host = line.substring(5).trim();
                break;
            }
        }
        String kq = method + ";" + path + ";" + host;
        // ==================

        frameSend(sc, kq);
        sc.close();
    }

    static void frameSend(SocketChannel sc, String text) throws Exception {
        byte[] payload = text.getBytes("UTF-8");
        ByteBuffer buf = ByteBuffer.allocate(4 + payload.length);
        buf.putInt(payload.length);
        buf.put(payload);
        buf.flip();
        while (buf.hasRemaining()) sc.write(buf);
    }

    static String frameRecv(SocketChannel sc) throws Exception {
        ByteBuffer lenBuf = ByteBuffer.allocate(4);
        while (lenBuf.hasRemaining()) sc.read(lenBuf);
        lenBuf.flip();
        int len = lenBuf.getInt();
        ByteBuffer payBuf = ByteBuffer.allocate(len);
        while (payBuf.hasRemaining()) sc.read(payBuf);
        payBuf.flip();
        return Charset.forName("UTF-8").decode(payBuf).toString();
    }
}

 Bài 4 — Parse JSON đơn giản | qCode: h4VQFoET | Cổng: 2211

Đề bài:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2211 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng SocketChannel và ByteBuffer để trao đổi thông tin theo giao thức frame: 4 byte độ dài (int32) + payload (UTF-8).
Lưu ý: server & client đều phải đọc đủ dữ liệu bằng vòng lặp (readFully) do server luôn chia nhỏ dữ liệu khi gửi. Trình tự trao đổi như sau:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode.
   Ví dụ: B16DCCN999;ucpQ9zAh
b. Nhận dữ liệu từ server gồm đúng 2 frame liên tiếp. Payload của mỗi frame là một phần của cùng một chuỗi JSON đơn giản trên một dòng (không xuống dòng). Client phải nối 2 payload theo đúng thứ tự để thu được chuỗi JSON hoàn chỉnh.
c. Trích xuất các trường event, user, ok và gửi lại lên server theo định dạng event=<event>;user=<user>;ok=<0|1> (true=1, false=0).
d. Đóng kết nối và kết thúc chương trình.

Code:
import java.util.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class Main {
    public static void main(String[] args) throws Exception {
        SocketChannel sc = SocketChannel.open(
            new InetSocketAddress("36.50.135.242", 2211));
        sc.configureBlocking(true);

        frameSend(sc, "B23DCCN926;h4VQFoET");

        // ===== LOGIC =====
        String json = frameRecv(sc) + frameRecv(sc);
        String event = extract(json, "event");
        String user  = extract(json, "user");
        String ok    = extract(json, "ok").equals("true") ? "1" : "0";
        String kq = "event=" + event + ";user=" + user + ";ok=" + ok;
        // ==================

        frameSend(sc, kq);
        sc.close();
    }

    static String extract(String json, String key) {
        String pattern = "\"" + key + "\":";
        int i = json.indexOf(pattern) + pattern.length();
        if (json.charAt(i) == '"') {
            int end = json.indexOf('"', i + 1);
            return json.substring(i + 1, end);
        } else {
            int end = json.indexOf(',', i);
            if (end == -1) end = json.indexOf('}', i);
            return json.substring(i, end).trim();
        }
    }

    static void frameSend(SocketChannel sc, String text) throws Exception {
        byte[] payload = text.getBytes("UTF-8");
        ByteBuffer buf = ByteBuffer.allocate(4 + payload.length);
        buf.putInt(payload.length);
        buf.put(payload);
        buf.flip();
        while (buf.hasRemaining()) sc.write(buf);
    }

    static String frameRecv(SocketChannel sc) throws Exception {
        ByteBuffer lenBuf = ByteBuffer.allocate(4);
        while (lenBuf.hasRemaining()) sc.read(lenBuf);
        lenBuf.flip();
        int len = lenBuf.getInt();
        ByteBuffer payBuf = ByteBuffer.allocate(len);
        while (payBuf.hasRemaining()) sc.read(payBuf);
        payBuf.flip();
        return Charset.forName("UTF-8").decode(payBuf).toString();
    }
}
BYTE STREAM (InputStream / OutputStream — Cổng 2206)
BÀI 1: Khoảng Cách Nhỏ Nhất (zurZGVAs)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;FF49DC02"
b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự "," Ex: 1,3,9,19,33,20
c. Thực hiện tìm giá trị khoảng cách nhỏ nhất của các phần tử nằm trong chuỗi và hai giá trị lớn nhất tạo nên khoảng cách đó. Gửi lên server chuỗi gồm "khoảng cách nhỏ nhất, số thứ nhất, số thứ hai". Ex: 1,19,20
d. Đóng kết nối và kết thúc
CODE:
package tcp_tuhoc;
import java.io.*;
import java.util.*;
import java.net.*;
public class tam {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2206);
        socket.setSoTimeout(5000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;zurZGVAs".getBytes());
        out.flush();
        byte[]buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();
        String[]parts = s.split("\\,");
        ArrayList<Integer> ds = new ArrayList<>();
        for(String x : parts){
            ds.add(Integer.parseInt(x.trim()));
        }
        Collections.sort(ds);
        int minn = Integer.MAX_VALUE;
        int num1 = 0, num2 = 0;
        for(int i = 0; i <= ds.size() - 2; i++){
            int hieu = Math.abs(ds.get(i) - ds.get(i + 1));
            if(hieu < minn){
                minn = hieu;
                num1 = ds.get(i);
                num2 = ds.get(i+1);
            }
        }
        String kq = minn + "," + num1 + "," + num2;
        out.write(kq.getBytes());
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 2: Lớn Thứ Hai (uELfDKlC)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;2B3A6510"
b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ",". Ví dụ: 1,3,9,19,33,20
c. Tìm và gửi lên server giá trị lớn thứ hai cùng vị trí xuất hiện của nó trong chuỗi. Ví dụ: 20,5
d. Đóng kết nối và kết thúc chương trình.
CODE:
package tcp_tuhoc;
import java.io.*;
import java.util.*;
import java.net.*;
public class tam {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2206);
        socket.setSoTimeout(5000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;uELfDKlC".getBytes());
        out.flush();
        byte[]buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();
        String[]parts = s.split("\\,");
        ArrayList<Integer> ds = new ArrayList<>();
        for(String x : parts){ ds.add(Integer.parseInt(x.trim())); }
        int maxx = Integer.MIN_VALUE;
        int num1 = 0, num2 = 0;
        int vtri1 = 0, vtri2 = 0;
        for(int i = 0; i <= ds.size() - 1; i++){
            if(ds.get(i) > maxx){ maxx = ds.get(i); num1 = maxx; vtri1 = i; }
        }
        for(int i = 0; i <= ds.size() - 1; i++){
            if(i != vtri1 && ds.get(i) > num2){ num2 = ds.get(i); vtri2 = i; }
        }
        String kq = num2 + "," + vtri2;
        out.write(kq.getBytes());
        in.close(); out.close(); socket.close();
    }
}

 BÀI 3: Tổng Số Nguyên Tố
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;C89DAB45"
b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",". Ví dụ: "8,4,2,10,5,6,1,3"
c. Tính tổng của tất cả các số nguyên tố trong chuỗi và gửi kết quả lên server. Ví dụ: Với dãy "8,4,2,10,5,6,1,3", các số nguyên tố là 2, 5, 3, tổng là 10. Gửi lên server chuỗi "10".
d. Đóng kết nối và kết thúc chương trình.
CODE:
package tcp_tuhoc;
import java.util.*;
import java.io.*;
import java.net.*;
public class tmp {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2206);
        socket.setSoTimeout(5000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;QCODE".getBytes());
        out.flush();
        byte[]buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();
        String[]parts = s.split("\\,");
        ArrayList<Integer> ds = new ArrayList<>();
        for(String x : parts){ ds.add(Integer.parseInt(x.trim())); }
        int tong = 0;
        for(int i = 0; i <= ds.size() - 1; i++){
            if(nt(ds.get(i))){ tong += ds.get(i); }
        }
        String kq = tong + "";
        out.write(kq.getBytes());
        out.flush();
        in.close(); out.close(); socket.close();
    }
    public static boolean nt(int n){
        if(n < 2) return false;
        for(int i = 2; i <= Math.sqrt(n); i++){
            if(n % i == 0) return false;
        }
        return true;
    }
}
BÀI 4: Lũy Thừa (nxMRj8z)
ĐỀ BÀI:
Một chương trình server tại địa chỉ 172.188.19.218 hỗ trợ kết nối qua giao thức TCP tại cổng 1604 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B20DCCN999;nxMRj8z"
b. Nhận dữ liệu từ server là một chuỗi gồm hai giá trị nguyên a, b được phân tách với nhau bằng "|". Ví dụ: 2|5
c. Thực hiện tìm giá trị a^b và gửi lên server. Ví dụ: 32
d. Đóng kết nối và kết thúc chương trình.
CODE:
package tcp_tuhoc;
import java.util.*;
import java.io.*;
import java.net.*;
public class tmp {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("172.188.19.218", 1604);
        socket.setSoTimeout(5000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;nxMRj8z".getBytes());
        out.flush();
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer,0,len).trim();
        String[]parts = s.split("\\|");
        ArrayList<Integer> ds = new ArrayList<>();
        for(String x : parts){ ds.add(Integer.parseInt(x.trim())); }
        int a = ds.get(0);
        int b = ds.get(1);
        long luythua = (long)Math.pow(a, b);
        String kq = luythua + "";
        out.write(kq.getBytes());
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 5: Tổng Tuyệt Đối (PUh9Ki1)
ĐỀ BÀI:
Một chương trình server tại địa chỉ 172.188.19.218 hỗ trợ kết nối qua giao thức TCP tại cổng 1604 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B20DCCN999;PUh9Ki1"
b. Nhận dữ liệu từ server là một chuỗi gồm các giá trị nguyên được phân tách với nhau bằng "|". Ví dụ: 2|5|9|11
c. Thực hiện tìm giá trị tổng của các số nguyên trong chuỗi và gửi lên server. Ví dụ: 27
d. Đóng kết nối và kết thúc chương trình.
CODE:
package tcp_tuhoc;
import java.util.*;
import java.io.*;
import java.net.*;
public class tmp {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("172.188.19.218",1604);
        socket.setSoTimeout(5000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;PUh9Ki1".getBytes());
        out.flush();
        byte[]buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer,0,len).trim();
        String[]parts = s.split("\\|");
        ArrayList<Integer> ds = new ArrayList<>();
        for(String x : parts){ ds.add(Integer.parseInt(x.trim())); }
        long tong = 0;
        for(int i = 0; i <= ds.size() - 1; i++){ tong += ds.get(i); }
        String kq = tong + "";
        out.write(kq.getBytes());
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 6: Collatz (2B3A6510)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;2B3A6510"
b. Nhận dữ liệu từ server là một số nguyên n nhỏ hơn 400. Ví dụ: 7
c. Thực hiện các bước sau đây để sinh ra chuỗi từ số nguyên n ban đầu và gửi lên server:
   - Nếu n là số chẵn → n1 = n / 2
   - Nếu n là số lẻ → n1 = 3n + 1
   Lặp lại cho đến khi n = 1. Kết quả theo format "chuỗi kết quả; độ dài". Ví dụ: n=7 → "7 22 11 34 17 52 26 13 40 20 10 5 16 8 4 2 1; 17"
d. Đóng kết nối và kết thúc chương trình.
CODE:
package tcp_tuhoc;
import java.util.*;
import java.net.*;
import java.io.*;
public class collazt {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242", 2206);
        socket.setSoTimeout(5000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;2B3A6510".getBytes());
        out.flush();
        byte[]buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer,0,len).trim();
        String[]parts = s.split(",");
        ArrayList<Integer> ds = new ArrayList<>();
        for(String x : parts){ ds.add(Integer.parseInt(x.trim())); }
        int n = ds.get(0);
        StringBuilder sb = new StringBuilder();
        int stt = 1;
        sb.append(n);
        while(n != 1){
            if(n % 2 == 0) n = n /2;
            else n = 3 * n + 1;
            sb.append("," + n);
            stt++;
        }
        String kq = sb.toString() + ";" + stt;
        out.write(kq.getBytes());
        out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 7: Hai Số Gần Trung Bình (TL9Pol9D)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;D45EFA12"
b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",". Ví dụ: "10,5,15,20,25,30,35"
c. Xác định hai số trong dãy có tổng gần nhất với gấp đôi giá trị trung bình. Ví dụ: gấp đôi trung bình = 40, hai số gần nhất là 15 và 25 → gửi "15,25"
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.util.*;
import java.io.*;
import java.net.*;
public class Bai1 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        String code = "B23DCCN926;TL9Pol9D";
        out.write(code.getBytes());
        out.flush();
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();
        String[] parts = s.split(",");
        int[] arr = new int[parts.length];
        long sum = 0;
        for (int i = 0; i < parts.length; i++) {
            arr[i] = Integer.parseInt(parts[i].trim());
            sum += arr[i];
        }
        double target = 2.0 * sum / arr.length;
        int[] sorted = arr.clone();
        Arrays.sort(sorted);
        int left = 0, right = sorted.length - 1;
        int bestA = sorted[0], bestB = sorted[1];
        double bestDiff = Double.MAX_VALUE;
        while (left < right) {
            double curSum = sorted[left] + sorted[right];
            double diff = Math.abs(curSum - target);
            if (diff < bestDiff) { bestDiff = diff; bestA = sorted[left]; bestB = sorted[right]; }
            if (curSum < target) left++;
            else right--;
        }
        String result = bestA + "," + bestB;
        out.write(result.getBytes());
        out.flush();
        socket.close();
    }
}

 BÀI 8: Dãy Con Không Lặp Dài Nhất (HyHAk4P5)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client thực hiện kết nối tới server sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;10048F28"
b. Nhận chuỗi ký tự s từ server. Ví dụ: "abcabcbb"
c. Tìm và gửi lên server chuỗi con dài nhất từ chuỗi nhận được mà không có ký tự lặp lại theo format "longestsubstring;length". Ví dụ: "abc;3"
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.util.*;
import java.io.*;
import java.net.*;
public class Bai8 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;HyHAk4P5".getBytes());
        out.flush();
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();
        Map<Character, Integer> map = new HashMap<>();
        int left = 0, bestStart = 0, bestLen = 0;
        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            if (map.containsKey(c) && map.get(c) >= left) { left = map.get(c) + 1; }
            map.put(c, right);
            int curLen = right - left + 1;
            if (curLen > bestLen) { bestLen = curLen; bestStart = left; }
        }
        String best = s.substring(bestStart, bestStart + bestLen);
        String result = best + ";" + bestLen;
        out.write(result.getBytes());
        out.flush();
        socket.close();
    }
}

 BÀI 9: Dãy Con Tăng Liên Tiếp Dài Nhất (RnPqP3f7)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;76B68B3B"
b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách bởi ký tự ",". Ví dụ: 5,10,20,25,50,40,30,35
c. Tìm chuỗi con tăng dần dài nhất và gửi độ dài của chuỗi đó lên server. Ví dụ: 5,10,20,25 có độ dài 4.
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.util.*;
import java.io.*;
import java.net.*;
public class Bai7 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;RnPqP3f7".getBytes());
        out.flush();
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();
        String[] parts = s.split(",");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) arr[i] = Integer.parseInt(parts[i].trim());
        int best = 1, cur = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1]) { cur++; if (cur > best) best = cur; }
            else { cur = 1; }
        }
        out.write(String.valueOf(best).getBytes());
        out.flush();
        socket.close();
    }
}

 BÀI 10: Vị Trí Cân Bằng (zmNHK0Y7)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu xây dựng chương trình client thực hiện kết nối tới server sử dụng luồng byte dữ liệu (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;E56FAB67"
b. Nhận dữ liệu từ server là một chuỗi các số nguyên được phân tách bởi ký tự ",". Ví dụ: "3,7,2,5,8,1"
c. Tìm vị trí mà độ lệch của tổng bên trái và tổng bên phải là nhỏ nhất. Gửi lên server vị trí đó, tổng trái, tổng phải và độ lệch. Ví dụ: với dãy "3,7,2,5,8,1", vị trí 3 có độ lệch nhỏ nhất = 3 → Kết quả gửi server: "3,12,9,3"
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.util.*;
import java.io.*;
import java.net.*;
public class Bai3 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;zmNHK0Y7".getBytes());
        out.flush();
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();
        String[] parts = s.split(",");
        int[] arr = new int[parts.length];
        int totalSum = 0;
        for (int i = 0; i < parts.length; i++) { arr[i] = Integer.parseInt(parts[i].trim()); totalSum += arr[i]; }
        int bestPos = 1;
        int bestLeft = 0, bestRight = totalSum - arr[0];
        int bestDiff = Math.abs(bestLeft - bestRight);
        int leftSum = 0;
        for (int i = 0; i < arr.length; i++) {
            int rightSum = totalSum - leftSum - arr[i];
            int diff = Math.abs(leftSum - rightSum);
            if (diff < bestDiff) { bestDiff = diff; bestPos = i + 1; bestLeft = leftSum; bestRight = rightSum; }
            leftSum += arr[i];
        }
        String result = bestPos + "," + bestLeft + "," + bestRight + "," + bestDiff;
        out.write(result.getBytes());
        out.flush();
        socket.close();
    }
}

 BÀI 11: Sắp Xếp Chẵn Lẻ (rMdCliDV)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B16DCCN999;A63D9404"
b. Nhận dữ liệu từ server là một chuỗi các số nguyên được sắp xếp ngẫu nhiên, các số được phân tách nhau bởi ký tự ",". Ví dụ: "2,15,4,3,6,8,10,7,1"
c. Sắp xếp tăng dần các giá trị chẵn và sau đó tăng dần các giá trị lẻ trong dãy số. Ví dụ: "[2, 4, 6, 8, 10];[1, 3, 7, 15]". Gửi chuỗi được sắp xếp này lên server.
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.util.*;
import java.io.*;
import java.net.*;
public class Bai10 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("203.162.10.109", 2206);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        out.write("B23DCCN926;rMdCliDV".getBytes());
        out.flush();
        byte[] buffer = new byte[1024];
        int len = in.read(buffer);
        String s = new String(buffer, 0, len).trim();
        String[] parts = s.split(",");
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();
        for (String p : parts) {
            int n = Integer.parseInt(p.trim());
            if (n % 2 == 0) even.add(n);
            else odd.add(n);
        }
        Collections.sort(even); Collections.sort(odd);
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < even.size(); i++) { if (i > 0) sb.append(", "); sb.append(even.get(i)); }
        sb.append("];[");
        for (int i = 0; i < odd.size(); i++) { if (i > 0) sb.append(", "); sb.append(odd.get(i)); }
        sb.append("]");
        out.write(sb.toString().getBytes());
        out.flush();
        socket.close();
    }
}
DATA STREAM (DataInputStream / DataOutputStream — Cổng 2207)
BÀI 1: Tổng Tích (IPp4zyGL)
ĐỀ BÀI:
Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s), yêu cầu xây dựng chương trình (tạm gọi là client) thực hiện kết nối tới server tại cổng 2207, sử dụng luồng byte dữ liệu (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode. Ví dụ: B15DCCN999;1D25ED92
b. Nhận lần lượt hai số nguyên a và b từ server
c. Thực hiện tính toán tổng, tích và gửi lần lượt từng giá trị theo đúng thứ tự trên lên server
d. Đóng kết nối và kết thúc
CODE:
import java.io.*;
import java.net.*;
public class data_tongtich {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2207);
        socket.setSoTimeout(5000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("B23DCCN926;IPp4zyGL");
        int a = in.readInt();
        int b = in.readInt();
        out.writeInt(a + b);
        out.writeInt(a * b);
        in.close(); out.close(); socket.close();
    }
}

 BÀI 2: Đổi Hệ Cơ Số (0LTGyX4p)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B10DCCN001;A1B2C3D4"
b. Nhận một số nguyên hệ thập phân từ server. Ví dụ: 255
c. Chuyển đổi số nguyên nhận được sang hai hệ cơ số 8 và 16. Gửi lần lượt chuỗi kết quả lên server. Ví dụ: Với số 255 hệ thập phân, kết quả gửi lên sẽ là một chuỗi dạng "377;FF"
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
public class HeCoSo8Va16 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2207);
        socket.setSoTimeout(5000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("B23DCCN926;0LTGyX4p");
        int n = in.readInt();
        String octal = Integer.toOctalString(n);
        String hex = Integer.toHexString(n).toUpperCase();
        String result = octal + ";" + hex;
        out.writeUTF(result);
        in.close(); out.close(); socket.close();
    }
}

 BÀI 3: UCLN + BCNN + Tổng + Tích (nkBwM6AE)
ĐỀ BÀI:
Một chương trình máy chủ cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên bằng các byte stream (DataInputStream/DataOutputStream) để trao đổi thông tin theo trình tự sau:
a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;B1F1FDCD"
b. Nhận hai số nguyên a và b tương ứng từ máy chủ
c. Tính ước chung lớn nhất, bội chung nhỏ nhất, tổng, tích. Gửi từng giá trị số nguyên theo thứ tự trên đến máy chủ.
d. Đóng kết nối và kết thúc chương trình.
CODE:
package tcp_tuhoc;
import java.io.*;
import java.util.*;
import java.net.*;
public class tam2 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242",2207);
        socket.setSoTimeout(5000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("B23DCCN926;nkBwM6AE");
        out.flush();
        int a = in.readInt();
        int b = in.readInt();
        int uc = ucln(a,b);
        int bc = bcnn(a,b);
        int tong = a + b;
        int tich = a * b;
        out.writeInt(uc);
        out.writeInt(bc);
        out.writeInt(tong);
        out.writeInt(tich);
        in.close(); out.close(); socket.close();
    }
    public static int ucln(int a, int b){
        while(b != 0){ int r = a % b; a = b; b = r; }
        return a;
    }
    public static int bcnn(int a, int b){ return a / ucln(a, b) * b; }
}

 BÀI 4: Caesar Giải Mã (NYk58hZP)
ĐỀ BÀI:
Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký tự A sẽ được thay thế bằng ký tự D.
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng chương trình client tương tác với server trên, sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng studentCode;qCode. Ví dụ: B15DCCN999;D68C93F7
b. Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên
c. Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
public class data_caesar {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2207);
        socket.setSoTimeout(5000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("B23DCCN926;NYk58hZP");
        String encoded = in.readUTF();
        int s = in.readInt();
        s = ((s % 26) + 26) % 26;
        StringBuilder sb = new StringBuilder();
        for (char c : encoded.toCharArray()) {
            if (c >= 'A' && c <= 'Z') { sb.append((char) ('A' + (c - 'A' - s + 26) % 26)); }
            else if (c >= 'a' && c <= 'z') { sb.append((char) ('a' + (c - 'a' - s + 26) % 26)); }
            else { sb.append(c); }
        }
        out.writeUTF(sb.toString());
        in.close(); out.close(); socket.close();
    }
}

 BÀI 5: Đổi Chiều Biến Thiên (oNGj55wV)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua TCP tại cổng 807 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B10DCCN002;B4C5D6E7"
b. Nhận chuỗi chứa mảng số nguyên từ server, các phần tử được phân tách bởi dấu phẩy ",". Ví dụ: "1,3,2,5,4,7,6"
c. Tính số lần đổi chiều và tổng độ biến thiên trong dãy số. Đổi chiều: Khi dãy chuyển từ tăng sang giảm hoặc từ giảm sang tăng. Độ biến thiên: Tổng giá trị tuyệt đối của các hiệu số liên tiếp. Gửi lần lượt lên server: số nguyên đại diện cho số lần đổi chiều, sau đó là số nguyên đại diện cho tổng độ biến thiên. Ví dụ: Với mảng "1,3,2,5,4,7,6", số lần đổi chiều: 5 lần, Tổng độ biến thiên 11 → Gửi lần lượt số nguyên 5 và 11 lên server.
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
public class DoiChieuBienThien {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 807);
        socket.setSoTimeout(5000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("B23DCCN926;oNGj55wV");
        String s = in.readUTF();
        String[] parts = s.split(",");
        int[] arr = new int[parts.length];
        for (int i = 0; i < parts.length; i++) { arr[i] = Integer.parseInt(parts[i].trim()); }
        int doiChieu = 0, bienThien = 0;
        for (int i = 1; i < arr.length; i++) { bienThien += Math.abs(arr[i] - arr[i - 1]); }
        for (int i = 2; i < arr.length; i++) {
            int prev = arr[i - 1] - arr[i - 2];
            int curr = arr[i] - arr[i - 1];
            if ((prev > 0 && curr < 0) || (prev < 0 && curr > 0)) { doiChieu++; }
        }
        out.writeInt(doiChieu);
        out.writeInt(bienThien);
        in.close(); out.close(); socket.close();
    }
}

 BÀI 6: Đảo Ngược Đoạn K (dCNDHojG)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu xây dựng chương trình client thực hiện giao tiếp với server sử dụng luồng data (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B10DCCN003;C6D7E8F9"
b. Nhận lần lượt: Một số nguyên k là độ dài đoạn. Chuỗi chứa mảng số nguyên, các phần tử được phân tách bởi dấu phẩy ",". Ví dụ: Nhận k = 3 và "1,2,3,4,5,6,7,8".
c. Thực hiện chia mảng thành các đoạn có độ dài k và đảo ngược mỗi đoạn, sau đó gửi mảng đã xử lý lên server. Ví dụ: Với k = 3 và mảng "1,2,3,4,5,6,7,8", kết quả là "3,2,1,6,5,4,8,7". Gửi chuỗi kết quả "3,2,1,6,5,4,8,7" lên server.
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class DaoNguocDoanDaiK {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2207);
        socket.setSoTimeout(5000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("B23DCCN926;dCNDHojG");
        int k = in.readInt();
        String s = in.readUTF();
        String[] parts = s.split(",");
        int n = parts.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) { arr[i] = Integer.parseInt(parts[i].trim()); }
        for (int i = 0; i < n; i += k) {
            int left = i, right = Math.min(i + k, n) - 1;
            while (left < right) { int tmp = arr[left]; arr[left] = arr[right]; arr[right] = tmp; left++; right--; }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) { if (i > 0) sb.append(","); sb.append(arr[i]); }
        out.writeUTF(sb.toString());
        in.close(); out.close(); socket.close();
    }
}

 BÀI 7: Tung Xúc Xắc (PpWEQ6F0)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua TCP tại cổng 2207 (hỗ trợ thời gian liên lạc tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng chương trình client tương tác với server bằng các byte stream (DataInputStream/DataOutputStream) để trao đổi thông tin theo trình tự sau:
a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". Ví dụ: "B10DCCN000;0D135D6A".
b. Nhận từ server một số nguyên n, là số lần tung xúc xắc. Ví dụ: Nếu bạn nhận được n = 21 từ máy chủ, có nghĩa bạn sẽ nhận giá trị tung xúc xắc 21 lần. Nhận từ server các giá trị sau mỗi lần tung xúc xắc. Ví dụ: Server gửi lần lượt 21 giá trị là 1,6,4,4,4,3,2,6,3,4,5,4,5,2,4,5,4,6,1,5,5
c. Tính xác suất xuất hiện của các giá trị [1,2,3,4,5,6] khi tung xúc sắc và gửi lần lượt xác suất này (dưới dạng float) lên server theo đúng thứ tự.
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
public class TungXucXac {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2207);
        socket.setSoTimeout(5000);
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF("B23DCCN926;PpWEQ6F0");
        int n = in.readInt();
        int[] count = new int[6];
        for (int i = 0; i < n; i++) { int val = in.readInt(); count[val - 1]++; }
        for (int i = 0; i < 6; i++) { out.writeFloat((float) count[i] / n); }
        in.close(); out.close(); socket.close();
    }
}

 CHARACTER STREAM (BufferedReader / BufferedWriter — Cổng 2208)
BÀI 1: Lọc .edu (kCAeDRzX)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng studentCode;qCode. Ví dụ: B15DCCN999;EC4F899B
b. Nhận một chuỗi ngẫu nhiên là danh sách các một số tên miền từ server. Ví dụ: giHgWHwkLf0Rd0.io, I7jpjuRw13D.io, wXf6GP3KP.vn, MdpIzhxDVtTFTF.edu, TUHuMfn25chmw.vn, HHjE9.com, 4hJld2m2yiweto.vn, y2L4SQwH.vn, s2aUrZGdzS.com, 4hXfJe9giAA.edu
c. Tìm kiếm các tên miền .edu và gửi lên server. Ví dụ: MdpIzhxDVtTFTF.edu, 4hXfJe9giAA.edu
d. Đóng kết nối và kết thúc chương trình.
CODE:
package tcp_tuhoc;
import java.io.*;
import java.util.*;
import java.net.*;
public class bai1_character {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;kCAeDRzX");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[] parts = s.split(", ");
        StringBuilder sb = new StringBuilder();
        for (String x : parts) { if (x.trim().endsWith(".edu")) { sb.append(x.trim()).append(", "); } }
        String kq = sb.toString();
        if (kq.endsWith(", ")) { kq = kq.substring(0, kq.length() - 2); }
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 2: Bỏ Nguyên Âm (x8c45mq)
ĐỀ BÀI:
Một chương trình server tại địa chỉ 172.188.19.218 cho phép kết nối qua giao thức TCP tại cổng 1606 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B20DCCN999;ABCDEF" với ABCDEF là mã bài tập đã đề cập ở trên.
b. Nhận một chuỗi từ server (Chỉ chứa kí tự thường).
c. Thực hiện loại bỏ các nguyên âm trong chuỗi và gửi kết quả lên server.
d. Đóng kết nối và kết thúc.
CODE:
package tcp_tuhoc;
import java.util.*;
import java.net.*;
import java.io.*;
public class charraccterr {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("172.188.19.218",1606);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;x8c45mq");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[]parts = s.split("");
        String nguyenam = "ueoai";
        StringBuilder sb = new StringBuilder();
        for(String x : parts){ if(!nguyenam.contains(x)){ sb.append(x); } }
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 3: Đảo Ngược Chuỗi
ĐỀ BÀI:
Một chương trình server tại địa chỉ 172.188.19.218 cho phép kết nối qua giao thức TCP tại cổng 1606 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi chuỗi là mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B20DCCN999;ABCDEF"
b. Nhận một chuỗi từ server.
c. Thực hiện đảo ngược lại chuỗi và gửi lên server.
d. Đóng kết nối và kết thúc.
CODE:
package tcp_tuhoc;
import java.util.*;
import java.io.*;
import java.net.*;
public class denkhithivoivavoivangdoitay {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("172.188.19.218",1606);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;QCODE");
        out.newLine(); out.flush();
        String s = in.readLine();
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 4: Sort Từ Điển (lXo9m21K)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client thực hiện kết nối tới server và sử dụng luồng ký tự (BufferedWriter/BufferedReader) để trao đổi thông tin theo kịch bản sau:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;X1107ABC".
b. Nhận từ server một chuỗi ngẫu nhiên chứa nhiều từ, các từ phân tách bởi khoảng trắng.
c. Thực hiện các bước xử lý: Bước 1: Tách chuỗi thành các từ dựa trên khoảng trắng. Bước 2: Sắp xếp các từ theo thứ tự từ điển (có phân biệt chữ cái hoa thường).
d. Gửi lại chuỗi đã sắp xếp theo thứ tự từ điển lên server.
CODE:
package tcp_tuhoc;
import java.util.*;
import java.io.*;
import java.net.*;
public class denkhithivoivavoivangdoitay {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("172.188.19.218",1606);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;lXo9m21K");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[]parts = s.split(" ");
        ArrayList<String> ds = new ArrayList<>();
        for(String x : parts){ ds.add(x.trim()); }
        Collections.sort(ds);
        StringBuilder sb = new StringBuilder();
        for(String x : ds){ sb.append(x.trim()).append(" "); }
        String kq = sb.toString();
        if(kq.endsWith(" ")){ kq = kq.substring(0, kq.length() - 1); }
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 5: Sort Độ Dài (we3kcWxZ)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;C1234567"
b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng. Ví dụ: "hello world this is a test example"
c. Sắp xếp các từ trong chuỗi theo độ dài, thứ tự xuất hiện. Gửi danh sách các từ theo từng nhóm về server theo định dạng: "a, is, this, test, hello, world, example".
d. Đóng kết nối và kết thúc chương trình.
CODE:
package tcp_tuhoc;
import java.util.*;
import java.io.*;
import java.net.*;
public class character5 {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("36.50.135.242",2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;we3kcWxZ");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[] parts = s.split(" ");
        ArrayList<String> ds = new ArrayList<>();
        for(String x : parts){ ds.add(x.trim()); }
        Collections.sort(ds, (a,b) -> a.length() - b.length());
        StringBuilder sb = new StringBuilder();
        for(String x : ds){ sb.append(x.trim()).append(" "); }
        String kq = sb.toString();
        if(kq.endsWith(" ")){ kq = kq.substring(0, kq.length() - 1); }
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 6: Nén RLE (ji3fQD3Q)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5 giây). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
a. Gửi một chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;1D08FX21"
b. Nhận từ server một chuỗi chứa nhiều từ, các từ được phân tách bởi khoảng trắng. Ví dụ: "hello world programming is fun"
c. Thực hiện đảo ngược từ và mã hóa RLE để nén chuỗi ("aabb" nén thành "a2b2"). Gửi chuỗi đã được xử lý lên server. Ví dụ: "ol2eh dlrow gnim2argorp si nuf".
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class NenRLE {
    static String xauDao(String s) { return new StringBuilder(s).reverse().toString(); }
    static String RLE(String s) {
        StringBuilder sb = new StringBuilder();
        int cnt = 1;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) { cnt++; }
            else { sb.append(s.charAt(i - 1)); if (cnt >= 2) sb.append(cnt); cnt = 1; }
        }
        sb.append(s.charAt(s.length() - 1));
        if (cnt >= 2) sb.append(cnt);
        return sb.toString();
    }
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;ji3fQD3Q");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[] parts = s.trim().split("\\s+");
        ArrayList<String> ds = new ArrayList<>();
        for (String word : parts) { ds.add(RLE(xauDao(word))); }
        String kq = String.join(" ", ds);
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 7: Hiệu Hai Tập Hợp (vYbP7vOA)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản sau:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;DE0C2BF0"
b. Nhận lần lượt hai chuỗi ngẫu nhiên từ server.
c. Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, yêu cầu giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi thứ nhất đã được xử lý lên server.
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class HieuHaiTapHop {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;vYbP7vOA");
        out.newLine(); out.flush();
        String s1 = in.readLine();
        String s2 = in.readLine();
        int[] cnt = new int[256];
        for (char c : s2.toCharArray()) cnt[c]++;
        StringBuilder sb = new StringBuilder();
        for (char c : s1.toCharArray()) { if (cnt[c] == 0) sb.append(c); }
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 8: Lọc Ký Tự Đặc Biệt + Trùng (mhUhFT2v)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác tới server sử dụng các luồng ký tự (BufferedReader/BufferedWriter) theo kịch bản dưới đây:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;7D6265E3"
b. Nhận một chuỗi ngẫu nhiên từ server.
c. Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của ký tự. Gửi chuỗi đã được xử lý lên server.
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class LocKyTuDacBiet {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;mhUhFT2v");
        out.newLine(); out.flush();
        String s = in.readLine();
        int[] cnt = new int[256];
        for (char c : s.toCharArray()) { if (Character.isLetter(c)) cnt[c]++; }
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (cnt[c] > 0 && Character.isLetter(c)) { sb.append(c); cnt[c] = 0; }
        }
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 9: Tách Chuỗi (uQWRjN4f)
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client thực hiện kết nối tới server và sử dụng luồng ký tự (BufferedWriter/BufferedReader) để trao đổi thông tin theo kịch bản:
a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". Ví dụ: "B15DCCN999;5E263AE1"
b. Nhận một chuỗi ngẫu nhiên từ server.
c. Tách chuỗi đã nhận thành 2 chuỗi và gửi lần lượt theo thứ tự lên server:
   i. Chuỗi thứ nhất gồm các ký tự và số (loại bỏ các ký tự đặc biệt)
   ii. Chuỗi thứ hai gồm các ký tự đặc biệt
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class TachChuoi {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;uQWRjN4f");
        out.newLine(); out.flush();
        String s = in.readLine();
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) s1.append(c);
            else s2.append(c);
        }
        out.write(s1.toString()); out.newLine(); out.flush();
        out.write(s2.toString()); out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 10: Vị Trí Cân Bằng CHARACTER (zmNHK0Y7)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2208 (thời gian tối đa 5s). Xây dựng chương trình client sử dụng luồng ký tự (BufferedReader/BufferedWriter) trao đổi theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi: "studentCode;qCode"
b. Nhận chuỗi số nguyên phân tách bởi dấu ",". Ví dụ: 1,3,5,2,4
c. Tìm vị trí index i (không phải đầu/cuối) sao cho |tổng trái - tổng phải| là nhỏ nhất. Gửi lên server chuỗi: vị trí,tổng trái,tổng phải,độ lệch. Ví dụ: 2,4,6,2
d. Đóng kết nối
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class ViTriCanBang {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;zmNHK0Y7");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[] parts = s.trim().split(",");
        ArrayList<Integer> ds = new ArrayList<>();
        for (String x : parts) ds.add(Integer.parseInt(x.trim()));
        int n = ds.size();
        int pos = 0, tongTrai = 0, tongPhai = 0;
        int doLech = Integer.MAX_VALUE;
        for (int i = 1; i < n - 1; i++) {
            int trai = 0, phai = 0;
            for (int j = 0; j < i; j++) trai += ds.get(j);
            for (int j = i + 1; j < n; j++) phai += ds.get(j);
            int hieu = Math.abs(trai - phai);
            if (hieu < doLech) { doLech = hieu; pos = i; tongTrai = trai; tongPhai = phai; }
        }
        String kq = pos + "," + tongTrai + "," + tongPhai + "," + doLech;
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 11: Đảo Ngược Đoạn K CHARACTER
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi mã sinh viên và mã bài tập lên server theo định dạng <MSV>;<mã bài>. Ví dụ: B23DCCN926;qCode
b. Nhận từ server số nguyên k (độ dài mỗi đoạn), sau đó nhận chuỗi các số nguyên phân tách bằng ",". Ví dụ: k=3, chuỗi=1,2,3,4,5,6,7
c. Chia dãy thành các đoạn độ dài k, đảo ngược từng đoạn rồi ghép lại, gửi lên server. Ví dụ: 3,2,1,6,5,4,7
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.util.*;
import java.io.*;
import java.net.*;
public class DaoNguocDoanDaiK {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;qCode");
        out.newLine(); out.flush();
        int k = Integer.parseInt(in.readLine().trim());
        String s = in.readLine();
        String[] parts = s.split(",");
        ArrayList<Integer> ds = new ArrayList<>();
        for (String x : parts) { ds.add(Integer.parseInt(x.trim())); }
        int n = ds.size();
        ArrayList<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i += k) {
            int j = Math.min(i + k - 1, n - 1);
            for (int o = j; o >= i; o--) { res.add(ds.get(o)); }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) { sb.append(res.get(i)); if (i != n - 1) sb.append(","); }
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 12: Dãy Con Không Lặp Dài Nhất CHARACTER
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi mã sinh viên và mã bài tập lên server theo định dạng <MSV>;<mã bài>. Ví dụ: B23DCCN926;qCode
b. Nhận từ server một chuỗi ký tự bất kỳ. Ví dụ: abcabcbb
c. Tìm dãy con liên tiếp dài nhất không có ký tự lặp, gửi lên server theo định dạng <dãy con>;<độ dài>. Ví dụ: abc;3
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.util.*;
import java.io.*;
import java.net.*;
public class DayConKhongLapDaiNhat {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;qCode");
        out.newLine(); out.flush();
        String s = in.readLine();
        String strMax = "";
        for (int i = 0; i < s.length(); i++) {
            int[] cnt = new int[256];
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < s.length(); j++) {
                if (cnt[s.charAt(j)] == 1) break;
                cnt[s.charAt(j)] = 1;
                sb.append(s.charAt(j));
                if (sb.length() > strMax.length()) { strMax = sb.toString(); }
            }
        }
        String kq = strMax + ";" + strMax.length();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 13: Tổng 2 Số Gần Trung Bình CHARACTER
ĐỀ BÀI:
Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng ký tự (BufferedWriter/BufferedReader) theo kịch bản sau:
a. Gửi mã sinh viên và mã bài tập lên server theo định dạng <MSV>;<mã bài>. Ví dụ: B23DCCN926;qCode
b. Nhận từ server một dãy số nguyên phân tách bằng ",". Ví dụ: 3,1,4,1,5,9,2,6
c. Tìm cặp 2 số (không trùng vị trí) có tổng gần nhất với 2 lần trung bình cộng của dãy. Gửi lên server theo định dạng <số nhỏ>,<số lớn>. Ví dụ: 4,5
d. Đóng kết nối và kết thúc chương trình.
CODE:
import java.util.*;
import java.io.*;
import java.net.*;
public class Tong_x2_TB {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;qCode");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[] parts = s.split(",");
        ArrayList<Integer> ds = new ArrayList<>();
        for (String x : parts) { ds.add(Integer.parseInt(x.trim())); }
        Collections.sort(ds);
        int tong = 0;
        for (int x : ds) tong += x;
        float tbc = (float) tong / ds.size();
        float mucTieu = 2 * tbc;
        float kcach = Float.MAX_VALUE;
        int so1 = 0, so2 = 0;
        int n = ds.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                float hieu = Math.abs(ds.get(i) + ds.get(j) - mucTieu);
                if (hieu < kcach) { kcach = hieu; so1 = ds.get(i); so2 = ds.get(j); }
            }
        }
        String kq = so1 + "," + so2;
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 14: Tìm Từ Dài Nhất (oKOoB5Fc)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2208 (thời gian tối đa 5s). Xây dựng chương trình client sử dụng luồng ký tự (BufferedReader/BufferedWriter) trao đổi theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi: "studentCode;qCode"
b. Nhận một chuỗi các từ phân tách nhau bởi khoảng trắng. Ví dụ: hello world java programming
c. Tìm từ dài nhất trong chuỗi và vị trí xuất hiện (index trong chuỗi gốc). Gửi lên server 2 dòng riêng biệt: Dòng 1: từ dài nhất. Dòng 2: vị trí (index). Ví dụ: programming rồi 12
d. Đóng kết nối
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class TimTuDaiNhat {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;oKOoB5Fc");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[] parts = s.trim().split("\\s+");
        String tuDaiNhat = parts[0];
        for (String x : parts) { if (x.length() > tuDaiNhat.length()) { tuDaiNhat = x; } }
        int viTri = s.indexOf(tuDaiNhat);
        out.write(tuDaiNhat); out.newLine(); out.flush();
        out.write(String.valueOf(viTri)); out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 15: Dãy Con Tăng Dài Nhất LIS (XGIm2Fc7)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2208 (thời gian tối đa 5s). Xây dựng chương trình client sử dụng luồng ký tự (BufferedReader/BufferedWriter) trao đổi theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi: "studentCode;qCode"
b. Nhận chuỗi số nguyên phân tách bởi dấu ",". Ví dụ: 3,1,4,1,5,9,2,6
c. Tìm dãy con tăng dài nhất (LIS). Gửi lên server: phần tử 1,phần tử 2,...;độ dài. Ví dụ: 1,4,5,9;4
d. Đóng kết nối
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class DayConTangDaiNhat {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;XGIm2Fc7");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[] parts = s.trim().split(",");
        ArrayList<Integer> ds = new ArrayList<>();
        for (String x : parts) ds.add(Integer.parseInt(x.trim()));
        int n = ds.size();
        int[] f = new int[n];
        int[] trace = new int[n];
        Arrays.fill(f, 1); Arrays.fill(trace, -1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (ds.get(j) < ds.get(i) && f[j] + 1 > f[i]) { f[i] = f[j] + 1; trace[i] = j; }
            }
        }
        int maxLen = 0, endIdx = 0;
        for (int i = 0; i < n; i++) { if (f[i] > maxLen) { maxLen = f[i]; endIdx = i; } }
        ArrayList<Integer> lis = new ArrayList<>();
        while (endIdx != -1) { lis.add(0, ds.get(endIdx)); endIdx = trace[endIdx]; }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lis.size(); i++) { sb.append(lis.get(i)); if (i != lis.size() - 1) sb.append(","); }
        sb.append(";").append(lis.size());
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 16: Đếm Ký Tự Lặp (CVkVQheX)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2208 (thời gian tối đa 5s). Xây dựng chương trình client sử dụng luồng ký tự (BufferedReader/BufferedWriter) trao đổi theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi: "studentCode;qCode"
b. Nhận một chuỗi ngẫu nhiên từ server. Ví dụ: hello world
c. Đếm số lần xuất hiện của từng ký tự chữ/số (bỏ qua ký tự đặc biệt). Gửi lên server các ký tự xuất hiện >= 2 lần, theo thứ tự xuất hiện đầu tiên trong chuỗi, format: ký tự:số lần,ký tự:số lần,... Ví dụ: l:3,o:2,
d. Đóng kết nối
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class Dem {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;CVkVQheX");
        out.newLine(); out.flush();
        String s = in.readLine();
        int[] cnt = new int[256];
        for (char c : s.toCharArray()) { if (Character.isLetterOrDigit(c)) cnt[c]++; }
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (cnt[c] >= 2) { sb.append(c).append(":").append(cnt[c]).append(","); cnt[c] = 0; }
        }
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 17: Đổi Chiều Biến Thiên CHARACTER (oNGj55wV)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2208 (thời gian tối đa 5s). Xây dựng chương trình client sử dụng luồng ký tự (BufferedReader/BufferedWriter) trao đổi theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi: "studentCode;qCode"
b. Nhận chuỗi số nguyên phân tách bởi dấu ",". Ví dụ: 1,5,3,7,2,6
c. Tính 2 giá trị và gửi lên server 2 dòng riêng biệt: Dòng 1: số lần đổi chiều (phần tử là cực trị cục bộ). Dòng 2: tổng biến thiên = tổng |a[i] - a[i+1]|. Ví dụ: 3 rồi 18
d. Đóng kết nối
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class DoiChieuBienThien {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;oNGj55wV");
        out.newLine(); out.flush();
        String s = in.readLine();
        String[] parts = s.trim().split(",");
        ArrayList<Integer> ds = new ArrayList<>();
        for (String x : parts) ds.add(Integer.parseInt(x.trim()));
        int n = ds.size();
        int bienThien = 0;
        for (int i = 0; i < n - 1; i++) { bienThien += Math.abs(ds.get(i) - ds.get(i + 1)); }
        int doiChieu = 0;
        for (int i = 1; i < n - 1; i++) {
            boolean cucTieu = ds.get(i) < ds.get(i-1) && ds.get(i) < ds.get(i+1);
            boolean cucDai = ds.get(i) > ds.get(i-1) && ds.get(i) > ds.get(i+1);
            if (cucTieu || cucDai) doiChieu++;
        }
        out.write(String.valueOf(doiChieu)); out.newLine(); out.flush();
        out.write(String.valueOf(bienThien)); out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 18: Lọc Ký Tự (TuTa8p7)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2208 (thời gian tối đa 5s). Xây dựng chương trình client sử dụng luồng ký tự (BufferedReader/BufferedWriter) trao đổi theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi: "studentCode;qCode"
b. Nhận một chuỗi ngẫu nhiên từ server. Ví dụ: hello world java
c. Lấy các ký tự chữ cái xuất hiện trong chuỗi, mỗi ký tự chỉ lấy 1 lần, theo thứ tự xuất hiện đầu tiên. Gửi lên server chuỗi kết quả. Ví dụ: helowrdjva
d. Đóng kết nối
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class LocKyTu {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;TuTa8p7");
        out.newLine(); out.flush();
        String s = in.readLine();
        int[] cnt = new int[256];
        for (char c : s.toCharArray()) { if (Character.isLetter(c)) cnt[c]++; }
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) { if (cnt[c] > 0) { sb.append(c); cnt[c] = 0; } }
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}

 BÀI 19: Mã Hoá Caesar CHARACTER (doW5fnkq)
ĐỀ BÀI:
Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2208 (thời gian tối đa 5s). Xây dựng chương trình client sử dụng luồng ký tự (BufferedReader/BufferedWriter) trao đổi theo thứ tự:
a. Gửi mã sinh viên và mã câu hỏi: "studentCode;qCode"
b. Nhận 2 dòng từ server: Dòng 1: chuỗi cần giải mã. Dòng 2: số bước dịch k. Ví dụ: Khoor và 3
c. Giải mã Caesar — dịch ngược k bước (chỉ xử lý chữ cái, giữ nguyên ký tự khác, phân biệt hoa/thường). Gửi lên server chuỗi đã giải mã. Ví dụ: Hello
d. Đóng kết nối
CODE:
import java.io.*;
import java.net.*;
import java.util.*;
public class MaHoaCaesar {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("36.50.135.242", 2208);
        socket.setSoTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out.write("B23DCCN926;doW5fnkq");
        out.newLine(); out.flush();
        String s = in.readLine();
        int k = Integer.parseInt(in.readLine().trim());
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                c = (char) (((c - base - k + 26) % 26) + base);
            }
            sb.append(c);
        }
        String kq = sb.toString();
        out.write(kq);
        out.newLine(); out.flush();
        in.close(); out.close(); socket.close();
    }
}


