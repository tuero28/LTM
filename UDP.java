UDP OBJECT: ĐỀ ĐẦY ĐỦ + CODE CHUẨN (5 bài, cổng 2209)
Cổng 2209. Server gửi 8 byte requestId + đối tượng serialize. Client sửa rồi gửi lại cùng cấu trúc.
NetBeans: tạo package UDP (chữ hoa), class đặt trong đó, file Main0X.java cùng cấp với package.
KHUNG CHUNG
import java.util.*;
        import java.net.*;
        import java.io.*;
        import UDP.Lop;

public class Main0X {
    public static void main(String[] args) throws Exception {
        // 1. Chuẩn bị + gửi phiếu xin đề
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2209;
        String code = ";B23DCCN926;qCode";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        // 2. Nhận: 8 byte requestId + đối tượng
        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String requestId = new String(nhan.getData(), 0, 8);
        Lop o = (Lop) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();

        // 3. Sửa thuộc tính của o ...

        // 4. Gửi: 8 byte requestId + đối tượng
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes());
        new ObjectOutputStream(baos).writeObject(o);
        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));

        socket.close();
    }
}
Lưu ý quan trọng:
        - baos.write(requestId.getBytes()) PHẢI đứng TRƯỚC new ObjectOutputStream(baos)
- Buffer nhận 4096 byte (1024 không đủ)
- Class rút gọn: chỉ cần đúng serialVersionUID và đúng tên/kiểu thuộc tính, để public
────────────────────────────────────────────────────────────
BÀI C1. QUẢN LÝ SINH VIÊN  [qCode: 8VdcIxPQ]
        [Mã câu hỏi (qCode): 8VdcIxPQ]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau: Đối tượng trao đổi là thể hiện của lớp UDP.Student được mô tả:
        - Tên đầy đủ lớp: UDP.Student
- Các thuộc tính: id String, code String, name String, email String
- 02 Hàm khởi tạo: public Student(String id, String code, String name, String email) và public Student(String code)
- Trường dữ liệu: private static final long serialVersionUID = 20171107
Thực hiện:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059"
b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Student từ server. Trong đó, các thông tin được thiết lập gồm id và name.
c. Yêu cầu:
        - Chuẩn hóa tên theo quy tắc: Chữ cái đầu tiên in hoa, các chữ cái còn lại in thường và gán lại thuộc tính name của đối tượng
- Tạo email ptit.edu.vn từ tên người dùng bằng cách lấy tên và các chữ cái bắt đầu của họ và tên đệm. Ví dụ: nguyen van tuan nam -> namnvt@ptit.edu.vn. Gán giá trị này cho thuộc tính email của đối tượng nhận được
- Gửi thông điệp chứa đối tượng xử lý ở bước c lên Server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Student đã được sửa đổi.
d. Đóng socket và kết thúc chương trình.
Lưu ý: Hệ thống còn có mã câu hỏi 8yXucQXb cùng đề này — chỉ đổi qCode.
        File 1: Student.java (package UDP)
package UDP;
import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 20171107;
    public String id, code, name, email;
}
File 2: Main01.java
import java.util.*;
        import java.net.*;
        import java.io.*;
        import UDP.Student;

public class Main01 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2209;
        String code = ";B23DCCN926;8VdcIxPQ";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String requestId = new String(nhan.getData(), 0, 8);
        Student st = (Student) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();

        String[] w = st.name.trim().split("\\s+");
        String ten = "", email = w[w.length - 1].toLowerCase();
        for (int i = 0; i < w.length; i++) {
            ten += w[i].substring(0, 1).toUpperCase() + w[i].substring(1).toLowerCase() + " ";
            if (i < w.length - 1) email += Character.toLowerCase(w[i].charAt(0));
        }
        st.name = ten.trim();
        st.email = email + "@ptit.edu.vn";

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes());
        new ObjectOutputStream(baos).writeObject(st);
        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));

        socket.close();
    }
}
Nếu WA: kiểm tra trim() trước split, và baos.write() trước ObjectOutputStream.
        ────────────────────────────────────────────────────────────
BÀI C2. KHÁCH HÀNG  [qCode: 8nxPat2M]
        [Mã câu hỏi (qCode): 8nxPat2M]. Thông tin khách hàng được yêu cầu thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:
a. Tên khách hàng cần được chuẩn hóa theo định dạng mới. Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai
b. Ngày sinh của khách hàng đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. Ví dụ: 10-11-2012 -> 11/10/2012
c. Tài khoản khách hàng được tạo từ các chữ cái in thường được sinh tự động từ họ tên khách hàng. Ví dụ: nguyen van hai duong -> nvhduong
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server theo mô tả sau:
a. Đối tượng trao đổi là thể hiện của lớp UDP.Customer được mô tả như sau
- Tên đầy đủ của lớp: UDP.Customer
- Các thuộc tính: id String, code String, name String, dayOfBirth String, userName String
- Một Hàm khởi tạo với đầy đủ các thuộc tính được liệt kê ở trên
- Trường dữ liệu: private static final long serialVersionUID = 20151107;
b. Client giao tiếp với server theo các bước
- Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059"
        - Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Customer từ server. Trong đó, các thuộc tính id, code, name, dayOfBirth đã được thiết lập sẵn.
        - Yêu cầu thay đổi thông tin các thuộc tính như yêu cầu ở trên và gửi lại đối tượng khách hàng đã được sửa đổi lên server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Customer đã được sửa đổi.
- Đóng socket và kết thúc chương trình.
        File 1: Customer.java (package UDP)
package UDP;
import java.io.Serializable;

public class Customer implements Serializable {
    private static final long serialVersionUID = 20151107;
    public String id, code, name, dayOfBirth, userName;
}
File 2: Main02.java
import java.util.*;
        import java.net.*;
        import java.io.*;
        import UDP.Customer;

public class Main02 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2209;
        String code = ";B23DCCN926;8nxPat2M";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String requestId = new String(nhan.getData(), 0, 8);
        Customer c = (Customer) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();

        String[] w = c.name.trim().split("\\s+");
        int n = w.length;
        String ten = "", user = "";
        for (int i = 0; i < n - 1; i++) {
            ten += w[i].substring(0, 1).toUpperCase() + w[i].substring(1).toLowerCase() + " ";
            user += Character.toLowerCase(w[i].charAt(0));
        }
        c.name = (w[n - 1].toUpperCase() + ", " + ten).trim();
        c.userName = user + w[n - 1].toLowerCase();
        String[] d = c.dayOfBirth.split("-");
        c.dayOfBirth = d[1] + "/" + d[0] + "/" + d[2];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes());
        new ObjectOutputStream(baos).writeObject(c);
        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));

        socket.close();
    }
}
Chú ý: ngày d[1]/d[0]/d[2] (đổi chỗ ngày và tháng).
        ────────────────────────────────────────────────────────────
BÀI C3. SẢN PHẨM  [qCode: kZqFKEDL]
        [Mã câu hỏi (qCode): kZqFKEDL]. Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:
a. Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, ví dụ: "lenovo thinkpad T520" bị chuyển thành "T520 thinkpad lenovo"
b. Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client giao tiếp với server để gửi/nhận các sản phẩm theo mô tả dưới đây:
a. Đối tượng trao đổi là thể hiện của lớp Product được mô tả như sau
- Tên đầy đủ của lớp: UDP.Product
- Các thuộc tính: id String, code String, name String, quantity int
- Một hàm khởi tạo có đầy đủ các thuộc tính được liệt kê ở trên
- Trường dữ liệu: private static final long serialVersionUID = 20161107;
b. Giao tiếp với server theo kịch bản
- Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059"
        - Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Product từ server. Trong đối tượng này, các thuộc tính id, name và quantity đã được thiết lập giá trị.
- Sửa các thông tin sai của đối tượng về tên và số lượng như mô tả ở trên và gửi đối tượng vừa được sửa đổi lên server theo cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Product đã được sửa đổi.
- Đóng socket và kết thúc chương trình.
        File 1: Product.java (package UDP)
package UDP;
import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 20161107;
    public String id, code, name;
    public int quantity;
}
File 2: Main03.java
import java.util.*;
        import java.net.*;
        import java.io.*;
        import UDP.Product;

public class Main03 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2209;
        String code = ";B23DCCN926;kZqFKEDL";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String requestId = new String(nhan.getData(), 0, 8);
        Product p = (Product) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();

        String[] w = p.name.trim().split("\\s+");
        String t = w[0];
        w[0] = w[w.length - 1];
        w[w.length - 1] = t;
        p.name = String.join(" ", w);
        p.quantity = Integer.parseInt(new StringBuilder(p.quantity + "").reverse().toString());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes());
        new ObjectOutputStream(baos).writeObject(p);
        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));

        socket.close();
    }
}
────────────────────────────────────────────────────────────
BÀI C4. CHUẨN HOÁ THÔNG TIN SÁCH  [qCode: LFACr5Bi]
        [Mã câu hỏi (qCode): LFACr5Bi]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau: Đối tượng trao đổi là thể hiện của lớp UDP.Book được mô tả:
        - Tên đầy đủ lớp: UDP.Book
- Các thuộc tính: id (String), title (String), author (String), isbn (String), publishDate (String)
- Hàm khởi tạo: public Book(String id, String title, String author, String isbn, String publishDate)
- Trường dữ liệu: private static final long serialVersionUID = 20251107L
Thực hiện:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN005;eQkvAeId"
b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Book từ server. Trong đó, các thuộc tính id, title, author, isbn, và publishDate đã được thiết lập sẵn.
c. Thực hiện:
        1. Chuẩn hóa title: Với mỗi từ, viết hoa chữ cái đầu tiên, viết thường các chữ cái còn lại.
        2. Chuẩn hóa author theo định dạng "Họ, Tên": Họ là từ đầu tiên, viết hoa tất cả; Tên là các từ còn lại, mỗi từ viết hoa chữ cái đầu tiên, viết thường các chữ cái còn lại, giữa mỗi từ chỉ có đúng một dấu cách.
3. Chuẩn hóa mã ISBN theo định dạng "978-3-16-148410-0"
        4. Chuyển đổi publishDate từ yyyy-mm-dd sang mm/yyyy.
d. Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Book đã được sửa đổi.
e. Đóng socket và kết thúc chương trình.
Test ví dụ: Input title "wAsSegd NCAxhCY" → Output "Wassegd Ncaxhcy". Input author "cFPISv tlpiAvv TzfHf" → Output "CFPISV, Tlpiavv Tzfhf". ISBN 13 chữ số → xxx-x-xx-xxxxxx-x.
Lưu ý: Hệ thống còn có mã câu hỏi qAuSMw4o cùng đề này — chỉ đổi qCode.
        File 1: Book.java (package UDP)
package UDP;
import java.io.Serializable;

public class Book implements Serializable {
    private static final long serialVersionUID = 20251107L;
    public String id, title, author, isbn, publishDate;
}
File 2: Main04.java
import java.util.*;
        import java.net.*;
        import java.io.*;
        import UDP.Book;

public class Main04 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2209;
        String code = ";B23DCCN926;LFACr5Bi";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String requestId = new String(nhan.getData(), 0, 8);
        Book b = (Book) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();

        String[] t = b.title.trim().split("\\s+");
        String[] a = b.author.trim().split("\\s+");
        String title = "", ten = "";
        for (String x : t) title += x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase() + " ";
        for (int i = 1; i < a.length; i++) ten += a[i].substring(0, 1).toUpperCase() + a[i].substring(1).toLowerCase() + " ";
        b.title = title.trim();
        b.author = (a[0].toUpperCase() + ", " + ten).trim();
        String s = b.isbn;
        b.isbn = s.substring(0, 3) + "-" + s.substring(3, 4) + "-" + s.substring(4, 6) + "-" + s.substring(6, 12) + "-" + s.substring(12);
        String[] d = b.publishDate.trim().split("[-\\s]+");
        b.publishDate = d[1] + "/" + d[0];

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes());
        new ObjectOutputStream(baos).writeObject(b);
        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));

        socket.close();
    }
}
Chú ý: isbn cắt theo substring(0,3), (3,4), (4,6), (6,12), (12).
publishDate split bằng [-\\s]+ để xử lý khoảng trắng lạ.
        ────────────────────────────────────────────────────────────
BÀI C5. QUẢN LÝ THÔNG TIN NHÂN VIÊN  [qCode: ySsumsIE]
        [Mã câu hỏi (qCode): ySsumsIE]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2209. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản sau: Đối tượng trao đổi là thể hiện của lớp UDP.Employee được mô tả:
        - Tên đầy đủ lớp: UDP.Employee
- Các thuộc tính: id (String), name (String), salary (double), hireDate (String)
        - Hàm khởi tạo: public Employee(String id, String name, double salary, String hireDate)
- Trường dữ liệu: private static final long serialVersionUID = 20261107L
Thực hiện:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B23DCCN006;ITleSdqV"
b. Nhận thông điệp chứa: 08 byte đầu chứa chuỗi requestId, các byte còn lại chứa một đối tượng là thể hiện của lớp Employee từ server. Trong đó, các thuộc tính id, name, salary và hireDate đã được thiết lập sẵn.
c. Thực hiện:
        - Chuẩn hóa name: viết hoa chữ cái đầu của mỗi từ, ví dụ "john doe" thành "John Doe".
        - Tăng salary: tăng x% lương, với x bằng tổng các chữ số của năm sinh.
        - Chuyển đổi hireDate từ định dạng yyyy-mm-dd sang dd/mm/yyyy. Ví dụ: "2023-07-15" thành "15/07/2023".
        - Gửi lại đối tượng đã được chuẩn hóa về server với cấu trúc: 08 byte đầu chứa chuỗi requestId và các byte còn lại chứa đối tượng Employee đã được sửa đổi.
d. Đóng socket và kết thúc chương trình.
        File 1: Employee.java (package UDP)
package UDP;
import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 20261107L;
    public String id, name, hireDate;
    public double salary;
}
File 2: Main05.java
import java.util.*;
        import java.net.*;
        import java.io.*;
        import UDP.Employee;

public class Main05 {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2209;
        String code = ";B23DCCN926;ySsumsIE";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String requestId = new String(nhan.getData(), 0, 8);
        Employee e = (Employee) new ObjectInputStream(
                new ByteArrayInputStream(nhan.getData(), 8, nhan.getLength() - 8)).readObject();

        String[] t = e.name.trim().split("\\s+");
        String ten = "";
        for (String x : t) ten += x.substring(0, 1).toUpperCase() + x.substring(1).toLowerCase() + " ";
        e.name = ten.trim();
        String[] d = e.hireDate.trim().split("-");
        e.hireDate = d[2] + "/" + d[1] + "/" + d[0];
        int tong = 0;
        for (char c : d[0].toCharArray()) tong += c - '0';
        e.salary = e.salary * (1 + tong / 100.0);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(requestId.getBytes());
        new ObjectOutputStream(baos).writeObject(e);
        socket.send(new DatagramPacket(baos.toByteArray(), baos.size(), sA, port));

        socket.close();
    }
}
Chú ý: chia 100.0 (không phải 100) để tránh integer division. Lấy năm từ hireDate (d[0]) để tính tổng chữ số.
UDP: ĐỀ ĐẦY ĐỦ + CODE CHUẨN (DATA + STRING)
Gộp 2 dạng UDP: Phần A: DATA (9 bài, cổng 2207) và Phần B: STRING (11 bài, cổng 2208).
Đi thi: đổi qCode và cổng theo đề trên màn hình. Host: 36.50.135.242
KHUNG CHUNG 4 CHẶNG
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Chuẩn bị + gửi phiếu xin đề
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207; // DATA = 2207, String = 2208
        String code = ";B23DCCN926;qCode";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        // 2. Nhận phiếu đề
        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        // 3. Xử lý + gửi phiếu kết quả
        String kq = ""; // bắt đầu bằng requestId + ";"
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        // 4. Đóng
        socket.close();
    }
}
────────────────────────────────────────────────────────────
PHẦN A: UDP DATA (cổng 2207)
BÀI A1. TỔNG HAI SỐ NGUYÊN LỚN  [qCode: 2sIjAYaU]
        [Mã câu hỏi (qCode): 2sIjAYaU]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN010;D3F9A7B8"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;a;b", với: requestId là chuỗi ngẫu nhiên duy nhất. a và b là chuỗi thể hiện hai số nguyên lớn (hơn hoặc bằng 100 chữ số). Ví dụ: "X1Y2Z3;9876543210;123456789"
c. Tính tổng và hiệu của hai số a và b, gửi thông điệp lên server theo định dạng "requestId;sum,difference". Ví dụ: "X1Y2Z3;9999999999,9753086421"
d. Đóng socket và kết thúc chương trình.
Chú ý: Yêu cầu sử dụng BigInteger. Tính a – b chứ không tính |a – b|.
Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;
        import java.math.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;2sIjAYaU";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[8192], 8192);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split("[;,]");
        String requestId = parts[0];
        BigInteger a = new BigInteger(parts[1].trim());
        BigInteger b = new BigInteger(parts[2].trim());
        String kq = requestId + ";" + a.add(b) + "," + a.subtract(b);
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
Nếu WA: đề tự mâu thuẫn ở dấu giữa tổng và hiệu (phần định dạng ghi ";", ví dụ ghi ","). Code đang dùng dấu ","; nếu WA thì đổi thành ";".
BÀI A2. PHẦN TỬ LỚN NHẤT CỦA DÃY CON  [qCode: iv00Hrq6 / mhH9GbNw]
        [Mã câu hỏi (qCode): iv00Hrq6]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B21DCCN795;ylrhZ6UM".
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n;k;z1,z2,...,zn", trong đó: requestId là chuỗi ngẫu nhiên duy nhất. n là số phần tử của mảng. k là kích thước cửa sổ trượt (k < n). z1 đến zn là n phần tử số nguyên của mảng.
c. Thực hiện tìm giá trị lớn nhất trong mỗi cửa sổ trượt với kích thước k, gửi theo định dạng "requestId;max1,max2,...,maxm". Ví dụ: "requestId;5;3;1,5,2,3,4" → Kết quả: "requestId;5,5,4"
d. Đóng socket và kết thúc chương trình.
Lưu ý: Hệ thống còn có mã câu hỏi mhH9GbNw cùng đề này: chỉ đổi qCode.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;iv00Hrq6";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        int k = Integer.parseInt(parts[2].trim());
        String[] zs = parts[3].split(",");
        int n = zs.length;
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) arr[i] = Integer.parseInt(zs[i].trim());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i + k <= n; i++) {
            int max = arr[i];
            for (int j = i; j < i + k; j++) {
                if (arr[j] > max) max = arr[j];
            }
            if (sb.length() > 0) sb.append(",");
            sb.append(max);
        }
        String kq = requestId + ";" + sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI A3. SẮP XẾP THEO VỊ TRÍ BAN ĐẦU  [qCode: aKZwZxWk]
        [Mã câu hỏi (qCode): aKZwZxWk]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;string", với: requestId là chuỗi ngẫu nhiên duy nhất. string là một chuỗi chứa các chuỗi con bị thay đổi vị trí. Ví dụ: "veM3xgA1g:4,IPFfgEanY:5,aWXlSzDwe:2,PHupvPc:3,PR3gH8ahN:6,UEEKHLIt:7,M6dpWTE:1"
c. Xử lý chuỗi xáo trộn và gửi về chuỗi sau khi sắp xếp: "requestId;string". Ví dụ: "M6dpWTE,aWXlSzDwe,PHupvPc,veM3xgA1g,IPFfgEanY,PR3gH8ahN,UEEKHLIt"
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;aKZwZxWk";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        TreeMap<Integer, String> mp = new TreeMap<>();
        for (String x : parts[1].split(",")) {
            String[] the = x.split(":");
            mp.put(Integer.parseInt(the[1].trim()), the[0].replaceAll("\\s", ""));
        }
        String kq = requestId + ";" + String.join(",", mp.values());
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI A4. GIÁ TRỊ NHỎ NHẤT – GIÁ TRỊ LỚN NHẤT  [qCode: uWKK8u3W / l2wVNbKC]
        [Mã câu hỏi (qCode): uWKK8u3W]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;DC73CA2E"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;a1,a2,...,a50". requestId là chuỗi ngẫu nhiên duy nhất. a1 -> a50 là 50 số nguyên ngẫu nhiên.
c. Thực hiện tìm giá trị lớn nhất và giá trị nhỏ nhất trong a1 -> a50 và gửi thông điệp lên server theo định dạng "requestId;max,min"
d. Đóng socket và kết thúc chương trình.
Lưu ý: Hệ thống còn có mã câu hỏi l2wVNbKC cùng đề này: chỉ đổi qCode.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;uWKK8u3W";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        String[] arr = parts[1].split(",");
        int max = Integer.parseInt(arr[0].trim());
        int min = max;
        for (String x : arr) {
            int v = Integer.parseInt(x.trim());
            if (v > max) max = v;
            if (v < min) min = v;
        }
        String kq = requestId + ";" + max + "," + min;
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI A5. NHỎ THỨ HAI VÀ LỚN THỨ HAI  [qCode: oQifsr90]
        [Mã câu hỏi (qCode): oQifsr90]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN004;99D9F604"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;z1,z2,...,z50". requestId là chuỗi ngẫu nhiên duy nhất. z1 -> z50 là 50 số nguyên ngẫu nhiên.
c. Thực hiện tính số lớn thứ hai và số nhỏ thứ hai trong z1 -> z50 và gửi thông điệp lên server theo định dạng "requestId;secondMax,secondMin"
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;oQifsr90";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        ArrayList<Integer> ds = new ArrayList<>();
        for (String x : parts[1].split(",")) ds.add(Integer.parseInt(x.trim()));
        Collections.sort(ds);
        int secondMin = ds.get(1);
        int secondMax = ds.get(ds.size() - 2);
        String kq = requestId + ";" + secondMax + "," + secondMin;
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
Nếu WA: đề không nói rõ khi có giá trị trùng nhau. Code đang không bỏ trùng. Nếu WA thì thử bỏ trùng bằng TreeSet rồi lấy phần tử thứ 2 từ đầu và từ cuối.
BÀI A6. TÌM SỐ CÒN THIẾU  [qCode: wAKCZwjj]
        [Mã câu hỏi (qCode): wAKCZwjj]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;73457A17"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n;A1,A2,...Am", với: requestId là chuỗi ngẫu nhiên duy nhất. n là một số ngẫu nhiên nhỏ hơn 100. A1, A2 ... Am (m <= n) là các giá trị ngẫu nhiên nhỏ hơn hoặc bằng n và có thể trùng nhau. Ví dụ: requestId;10;2,3,5,6,5
c. Tìm kiếm các giá trị còn thiếu và gửi lên server theo định dạng "requestId;B1,B2,...,Bm". Ví dụ: requestId;1,4,7,8,9,10
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;wAKCZwjj";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        int n = Integer.parseInt(parts[1].trim());
        String[] daBan = new String[0];
        if (parts.length > 2 && !parts[2].trim().isEmpty()) daBan = parts[2].split(",");
        StringBuilder sb = new StringBuilder();
        int dem = 0;
        for (int i = 1; i <= n; i++) {
            boolean coBan = false;
            for (String x : daBan) {
                if (Integer.parseInt(x.trim()) == i) coBan = true;
            }
            if (!coBan) {
                if (dem > 0) sb.append(",");
                sb.append(i);
                dem++;
            }
        }
        String kq = requestId + ";" + sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI A7. MÃ HOÁ CAESAR  [qCode: J5SE2YXc]
        [Mã câu hỏi (qCode): J5SE2YXc]. Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ được thay thế bằng một ký tự cách nó một đoạn s. Ví dụ: với s = 3 thì ký tự "A" sẽ được thay thế bằng ký tự "D". Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207.
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;825EE3A7"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;strEncode;s". requestId là chuỗi ngẫu nhiên duy nhất. strEncode là chuỗi thông điệp bị mã hóa. s là số nguyên chứa giá trị độ dịch của mã.
c. Giải mã tìm thông điệp ban đầu và gửi lên server theo định dạng "requestId;strDecode"
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;J5SE2YXc";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        int shift = Integer.parseInt(parts[2].trim());
        StringBuilder sb = new StringBuilder();
        for (char c : parts[1].toCharArray()) {
            char base = Character.isUpperCase(c) ? 'A' : 'a';
            sb.append(Character.isLetter(c) ? (char) ((c - base + shift % 26 + 26) % 26 + base) : c);
        }
        String kq = requestId + ";" + sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
Nếu WA: hướng xoay theo đề là cộng +s; nếu WA thì đổi "+ shift" thành "- shift".
BÀI A8. TỔNG CÁC CHỮ SỐ  [qCode: 0Iend7Pp]
        [Mã câu hỏi (qCode): 0Iend7Pp]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN011;A1F3D5"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;num", với: requestId là chuỗi ngẫu nhiên duy nhất. num là một số nguyên lớn.
c. Tính tổng các chữ số trong num và gửi lại tổng này về server theo định dạng "requestId;sumDigits".
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;0Iend7Pp";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        int sum = 0;
        for (char c : parts[1].toCharArray()) {
            if (Character.isDigit(c)) sum += c - '0';
        }
        String kq = requestId + ";" + sum;
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI A9. LIỆT KÊ SỐ NGUYÊN TỐ  [qCode: 78CCQ6xD]
        [Mã câu hỏi (qCode): 78CCQ6xD]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2207. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;F3E8B2D4".
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;n, n", với: requestId là chuỗi ngẫu nhiên duy nhất. n là một số nguyên ngẫu nhiên ≤ 100.
c. Tính và gửi về server danh sách n số nguyên tố đầu tiên theo định dạng "requestId;p1,p2,...,pk". Đây là "n số nguyên tố đầu tiên" (n = 5 thì ra 2, 3, 5, 7, 11), không phải "các số nguyên tố không vượt quá n".
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        String code = ";B23DCCN926;78CCQ6xD";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        int n = Integer.parseInt(parts[1].split(",")[0].trim());
        StringBuilder sb = new StringBuilder();
        int dem = 0;
        for (int x = 2; dem < n; x++) {
            boolean nt = true;
            for (int i = 2; i < x; i++) {
                if (x % i == 0) nt = false;
            }
            if (nt) {
                if (dem > 0) sb.append(",");
                sb.append(x);
                dem++;
            }
        }
        String kq = requestId + ";" + sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
────────────────────────────────────────────────────────────
PHẦN B: UDP STRING (cổng 2208)
BÀI B1. SẮP XẾP THEO TỪ ĐIỂN NGƯỢC  [qCode: 9UfU4Vky]
        [Mã câu hỏi (qCode): 9UfU4Vky]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN009;EF56GH78"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;data", với: requestId là chuỗi ngẫu nhiên duy nhất. data là một chuỗi ký tự chứa nhiều từ, được phân cách bởi dấu cách. Ví dụ: "EF56GH78;The quick brown fox"
c. Sắp xếp các từ trong chuỗi theo thứ tự từ điển ngược (z đến a) và gửi thông điệp lên server theo định dạng "requestId;word1,word2,...,wordN". Ví dụ: Với data = "The quick brown fox", kết quả là: "EF56GH78;quick,fox,brown,The"
d. Đóng socket và kết thúc chương trình.
Chú ý: Khi so sánh theo thứ tự từ điển thì không phân biệt chữ hoa chữ thường, đưa tất cả về cùng 1 kiểu rồi mới so sánh thì mới AC.
Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;9UfU4Vky";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        ArrayList<String> ds = new ArrayList<>(Arrays.asList(parts[1].trim().split("\\s+")));
        Collections.sort(ds, (x, y) -> y.toLowerCase().compareTo(x.toLowerCase()));
        String kq = requestId + ";" + String.join(",", ds);
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
Nếu WA: thử bỏ toLowerCase() trong comparator (đổi thành y.compareTo(x)).
BÀI B2. TỔNG HAI SỐ NHỊ PHÂN  [qCode: lIQVug9S]
        [Mã câu hỏi (qCode): lIQVug9S]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN000;XbYdNZ3".
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;b1,b2", trong đó: requestId là chuỗi ngẫu nhiên duy nhất. b1 là số nhị phân thứ nhất. b2 là số nhị phân thứ hai. Ví dụ: requestId;0100011111001101,1101000111110101
c. Thực hiện tính tổng hai số nhị phân nhận được, chuyển về dạng thập phân và gửi lên server theo định dạng "requestId;sum". Kết quả: requestId;72130
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;lIQVug9S";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split("[;,]");
        String requestId = parts[0];
        long b1 = Long.parseLong(parts[1].trim(), 2);
        long b2 = Long.parseLong(parts[2].trim(), 2);
        String kq = requestId + ";" + (b1 + b2);
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI B3. ĐẾM SỐ LẦN XUẤT HIỆN  [qCode: vSgxl3HQ / 1CSILxVz]
        [Mã câu hỏi (qCode): vSgxl3HQ]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208. Yêu cầu xây dựng chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi một thông điệp chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;9F8C2D3A".
b. Nhận một thông điệp từ server theo định dạng "requestId;data", với: requestId là chuỗi ngẫu nhiên duy nhất. data là một chuỗi ký tự liên tiếp cần xử lý. Ví dụ: "requestId;aaabbbccdaa"
c. Xử lý chuỗi bằng cách đếm số lượng ký tự và gom chúng lại theo định dạng "so_lan_ky_tu". Gửi kết quả về server theo định dạng "requestId;processedData". Ví dụ: Với chuỗi "aaabbbccdaa", kết quả sẽ là: "requestId;5a3b2c1d" (đếm trong cả xâu, liệt kê theo thứ tự xuất hiện lần đầu).
d. Đóng socket và kết thúc chương trình.
Lưu ý: đếm trong CẢ XÂU (5a = "aaa" đầu + "aa" cuối). Hệ thống còn có mã 1CSILxVz cùng đề.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;vSgxl3HQ";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        String data = parts[1];
        String daDem = "";
        StringBuilder sb = new StringBuilder();
        for (char c : data.toCharArray()) {
            if (daDem.indexOf(c) < 0) {
                daDem += c;
                int dem = 0;
                for (char d : data.toCharArray()) {
                    if (d == c) dem++;
                }
                sb.append(dem).append(c);
            }
        }
        String kq = requestId + ";" + sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI B4. LỌC KÝ TỰ  [qCode: GfeNSBMT]
        [Mã câu hỏi (qCode): GfeNSBMT]. Loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện. Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;06D6800D"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;strInput". requestId là chuỗi ngẫu nhiên duy nhất. strInput là chuỗi thông điệp cần xử lý.
c. Thực hiện loại bỏ ký tự đặc biệt, số, ký tự trùng và giữ nguyên thứ tự xuất hiện của chúng. Gửi thông điệp lên server theo định dạng "requestId;strOutput".
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;GfeNSBMT";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        StringBuilder sb = new StringBuilder();
        for (char c : parts[1].toCharArray()) {
            if (Character.isLetter(c) && sb.indexOf(String.valueOf(c)) < 0) sb.append(c);
        }
        String kq = requestId + ";" + sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
Nếu WA: đề không nói rõ chữ hoa và chữ thường có tính là trùng nhau không. Code đang coi "a" và "A" là hai ký tự khác nhau.
BÀI B5. HIỆU CỦA HAI TẬP KÝ TỰ  [qCode: JQCO3izC]
        [Mã câu hỏi (qCode): JQCO3izC]. Loại bỏ ký tự đặc biệt và ký tự trùng giữ nguyên thứ tự xuất hiện. Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;B34D51E0"
b. Nhận thông điệp là một chuỗi từ server theo định dạng "requestId;str1;str2". requestId là chuỗi ngẫu nhiên duy nhất. str1, str2 lần lượt là chuỗi thứ nhất và chuỗi thứ hai.
c. Loại bỏ các ký tự trong chuỗi thứ nhất mà xuất hiện trong chuỗi thứ hai, giữ nguyên thứ tự xuất hiện. Gửi thông điệp là một chuỗi lên server theo định dạng "requestId;strOutput".
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;JQCO3izC";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        String str1 = parts[1];
        String str2 = parts.length > 2 ? parts[2] : "";
        StringBuilder sb = new StringBuilder();
        for (char c : str1.toCharArray()) {
            if (str2.indexOf(c) < 0) sb.append(c);
        }
        String kq = requestId + ";" + sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
Nếu WA: thử bỏ thêm ký tự trùng và ký tự không phải chữ trong kết quả.
BÀI B6. KÝ TỰ XUẤT HIỆN NHIỀU LẦN NHẤT  [qCode: KAmLHOdi]  ✅ AC thật
[Mã câu hỏi (qCode): KAmLHOdi]. Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client tương tác với server:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059"
b. Nhận thông điệp từ server theo định dạng "requestId;data". requestId là một chuỗi ngẫu nhiên duy nhất. data là chuỗi dữ liệu đầu vào cần xử lý. Ví dụ: "requestId;Qnc8d5x78aldSGWWmaAAjyg3"
c. Tìm kiếm ký tự xuất hiện nhiều nhất trong chuỗi và gửi lên server theo định dạng "requestId;ký tự xuất hiện nhiều nhất: các vị trí xuất hiện ký tự đó". Ví dụ: "requestId;8:4,9," (vị trí đếm từ 1, dấu phẩy cuối giữ nguyên).
d. Đóng socket và kết thúc chương trình.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;KAmLHOdi";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        String data = parts[1];
        char best = ' ';
        int max = 0;
        for (char c : data.toCharArray()) {
            int dem = 0;
            for (char d : data.toCharArray()) {
                if (d == c) dem++;
            }
            if (dem > max) {
                max = dem;
                best = c;
            }
        }
        StringBuilder sb = new StringBuilder(requestId + ";" + best + ":");
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == best) sb.append(i + 1).append(",");
        }
        String kq = sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
Chú ý: dấu phẩy CUỐI CÙNG PHẢI GIỮ NGUYÊN như ví dụ "8:4,9,". Vị trí tính từ 1.
BÀI B7. CHUẨN HOÁ XÂU KÝ TỰ  [qCode: i28U6aGi / tmczgSLy / NRVwBVvx]  ✅ AC thật
[Mã câu hỏi (qCode): i28U6aGi]. Một chương trình server cho phép kết nối qua giao thức UDP tại cổng 2208. Yêu cầu là xây dựng một chương trình client trao đổi thông tin với server theo kịch bản:
a. Gửi thông điệp là một chuỗi chứa mã sinh viên và mã câu hỏi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;5B35BCC1"
b. Nhận thông điệp từ server theo định dạng "requestId;data". requestId là một chuỗi ngẫu nhiên duy nhất. data là chuỗi dữ liệu cần xử lý.
c. Xử lý chuẩn hóa chuỗi theo nguyên tắc: (i) Ký tự đầu tiên của từng từ trong chuỗi là in hoa. (ii) Các ký tự còn lại của chuỗi là in thường. Gửi thông điệp chứa chuỗi đã được chuẩn hóa lên server theo định dạng "requestId;data".
d. Đóng socket và kết thúc chương trình.
Lưu ý: Hệ thống còn có mã tmczgSLy và NRVwBVvx cùng đề này: chỉ đổi qCode.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;i28U6aGi";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";");
        String requestId = parts[0];
        String[] words = parts[1].trim().split("\\s+");
        StringBuilder sb = new StringBuilder(requestId + ";");
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            sb.append(w.substring(0, 1).toUpperCase()).append(w.substring(1).toLowerCase());
            if (i < words.length - 1) sb.append(" ");
        }
        String kq = sb.toString();
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI B8. CHUẨN HOÁ CHUỖI THÀNH SLUG (HAI PHA)  [qCode: V46plLn9]
        [Mã câu hỏi (qCode): V46plLn9]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208 theo cơ chế hai pha.
a. Gửi datagram đầu tiên chứa chuỗi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059".
b. Nhận phản hồi từ server theo định dạng "requestId;data". Ví dụ: "7Brz6QQA;REFUND ticket Payment Payment customer Payment customer ticket REFUND customer shipping shipping Payment.."
c. Chuyển chuỗi về chữ thường, loại bỏ dấu câu, gom nhiều khoảng trắng thành một khoảng trắng và thay khoảng trắng bằng dấu gạch ngang.
d. Gửi datagram nộp kết quả theo định dạng "requestId;answer". Ví dụ: "7Brz6QQA;refund-ticket-payment-payment-customer-payment-customer-ticket-refund-customer-shipping-shipping-payment".
e. Đóng kết nối hoặc kết thúc client sau khi nộp kết quả.
Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;V46plLn9";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";", 2);
        String requestId = parts[0];
        String r = parts[1].toLowerCase().replaceAll("\\p{Punct}", "").trim().replaceAll("\\s+", "-");
        String kq = requestId + ";" + r;
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
Nếu WA: thử đổi replaceAll("\\p{Punct}", "") thành replaceAll("\\p{Punct}", " ") (thay bằng khoảng trắng thay vì xóa hẳn) để tránh hai từ dính nhau.
BÀI B9. CHE EMAIL, SỐ ĐIỆN THOẠI, TOKEN TRONG LOG (HAI PHA)  [qCode: zFtVSq8y]
        [Mã câu hỏi (qCode): zFtVSq8y]. Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208 theo cơ chế hai pha.
a. Gửi datagram đầu tiên chứa chuỗi theo định dạng ";studentCode;qCode". Ví dụ: ";B15DCCN001;EE29C059".
b. Nhận phản hồi từ server theo định dạng "requestId;data". Ví dụ: "rBhDz2JJ;INFO user=minh email=minh0@example.com phone=0309453907 token=bDzj8Olxc9 action=refund".
        c. Che email, số điện thoại và token theo quy tắc [EMAIL], [PHONE], token=[TOKEN], giữ nguyên thứ tự các dòng log.
d. Gửi datagram nộp kết quả theo định dạng "requestId;answer". Ví dụ: "rBhDz2JJ;INFO user=minh email=[EMAIL] phone=[PHONE] token=[TOKEN] action=refund".
e. Đóng kết nối hoặc kết thúc client sau khi nộp kết quả.
Chú ý: split(";", 2) để không cắt nhầm dấu ; trong nội dung log. Buffer 4096 byte vì log nhiều dòng.
        Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        String code = ";B23DCCN926;zFtVSq8y";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        DatagramPacket nhan = new DatagramPacket(new byte[4096], 4096);
        socket.receive(nhan);
        String s = new String(nhan.getData(), 0, nhan.getLength());

        String[] parts = s.trim().split(";", 2);
        String requestId = parts[0];
        String r = parts[1].replaceAll("[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+", "[EMAIL]");
        r = r.replaceAll("phone=[^\\s,;]+", "phone=[PHONE]");
        r = r.replaceAll("token=[^\\s,;]+", "token=[TOKEN]");
        String kq = requestId + ";" + r;
        socket.send(new DatagramPacket(kq.getBytes(), kq.length(), sA, port));

        socket.close();
    }
}
BÀI B10. LỌC KÝ TỰ + LOẠI TRÙNG (BÀI MỚI TỪ HỆ THỐNG)  [qCode: cập nhật sau]
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208.
a. Gửi thông điệp theo định dạng ";studentCode;qCode".
b. Nhận thông điệp theo định dạng "requestId;strInput".
c. Loại bỏ ký tự đặc biệt và số (chỉ giữ chữ cái), loại bỏ ký tự đã xuất hiện trước đó, giữ nguyên thứ tự xuất hiện lần đầu. Ví dụ: "a1b!a2c" → "abc". Gửi theo định dạng "requestId;strOutput".
d. Đóng socket và kết thúc chương trình.
Khác B4 ở chỗ: B10 dùng ArrayList<Character> seen để kiểm tra trùng (ArrayList.contains thay vì sb.indexOf).
Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        // ĐỔI qCode theo đề thực tế
        String code = ";B23DCCN926;qCode_moi";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        // Nhận: requestId;strInput
        byte[] buf = new byte[4096];
        DatagramPacket nhan = new DatagramPacket(buf, buf.length);
        socket.receive(nhan);
        String received = new String(nhan.getData(), 0, nhan.getLength()).trim();
        String[] parts = received.split(";", 2);
        String requestId = parts[0];
        String strInput  = parts[1];

        // Xử lý: giữ chữ cái, loại trùng, giữ thứ tự xuất hiện
        ArrayList<Character> seen = new ArrayList<Character>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strInput.length(); i++) {
            char c = strInput.charAt(i);
            if (Character.isLetter(c) && !seen.contains(c)) {
                seen.add(c);
                sb.append(c);
            }
        }
        String strOutput = sb.toString();

        // Gửi: requestId;strOutput
        String result = requestId + ";" + strOutput;
        DatagramPacket gui2 = new DatagramPacket(result.getBytes(), result.length(), sA, port);
        socket.send(gui2);
        socket.close();
    }
}
Đổi qCode_moi thành mã câu hỏi thực tế khi thi. Port 2208.
BÀI B11. MASK LOG + ĐẾM ERROR/INFO/WARN (BÀI MỚI TỪ HỆ THỐNG)  [qCode: cập nhật sau]
Một chương trình server cho phép giao tiếp qua giao thức UDP tại cổng 2208.
a. Gửi thông điệp theo định dạng ";studentCode;qCode".
b. Nhận thông điệp theo định dạng "requestId;data" trong đó data là nhiều dòng log nối bằng ||.
c. Che email=... → email=[EMAIL], phone=... → phone=[PHONE], token=... → token=[TOKEN]. Đếm số dòng bắt đầu bằng ERROR, INFO, WARN.
d. Gửi theo định dạng: "requestId;maskedLog##ERROR=n;INFO=n;WARN=n"
Chú ý quan trọng:
        - Tách dòng log bằng split("\\|\\|") — phải escape đúng
- lines.length KHÔNG có dấu () — là thuộc tính mảng, không phải method
- Nối lại bằng || (thêm trước dòng từ index 1 trở đi)
        - Phần thống kê nối sau ## (hai dấu thăng)
Code chuẩn
import java.util.*;
        import java.net.*;
        import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress sA = InetAddress.getByName("36.50.135.242");
        int port = 2208;
        // ĐỔI qCode theo đề thực tế
        String code = ";B23DCCN926;qCode_moi";
        socket.send(new DatagramPacket(code.getBytes(), code.length(), sA, port));

        // Nhận: requestId;data (data = nhiều dòng log nối bằng ||)
        byte[] buf = new byte[65535];
        DatagramPacket nhan = new DatagramPacket(buf, buf.length);
        socket.receive(nhan);
        String received = new String(nhan.getData(), 0, nhan.getLength()).trim();
        String[] parts = received.split(";", 2);
        String requestId = parts[0];
        String data      = parts[1];

        // Xử lý: mask email/phone/token, đếm level
        String[] lines = data.split("\\|\\|");
        int cntError = 0, cntInfo = 0, cntWarn = 0;
        StringBuilder maskedLog = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.startsWith("ERROR")) cntError++;
            else if (line.startsWith("INFO"))  cntInfo++;
            else if (line.startsWith("WARN"))  cntWarn++;
            line = line.replaceAll("email=\\S+", "email=[EMAIL]");
            line = line.replaceAll("phone=\\S+", "phone=[PHONE]");
            line = line.replaceAll("token=\\S+", "token=[TOKEN]");
            if (i > 0) maskedLog.append("||");
            maskedLog.append(line);
        }

        // Gửi: requestId;maskedLog##ERROR=n;INFO=n;WARN=n
        String result = requestId + ";" + maskedLog.toString()
                + "##ERROR=" + cntError + ";INFO=" + cntInfo + ";WARN=" + cntWarn;
        DatagramPacket gui2 = new DatagramPacket(result.getBytes(), result.length(), sA, port);
        socket.send(gui2);
        socket.close();
    }
}
Đổi qCode_moi thành mã câu hỏi thực tế khi thi. Port 2208.
