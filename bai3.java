import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class DuLieuKhongHopLeException extends Exception {
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
}

abstract class SinhVien {
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected static final long DON_GIA = 450000L;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
    }

    public String getMaSV() {
        return maSV;
    }

    public abstract long hocPhi();

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi();
    }
}

class SinhVienCQ extends SinhVien {
    public SinhVienCQ(String maSV, int soTinChi) {
        super(maSV, "CQ", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA;
    }
}

class SinhVienCLC extends SinhVien {
    public SinhVienCLC(String maSV, int soTinChi) {
        super(maSV, "CLC", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA * 3 / 2;
    }
}

class SinhVienLT extends SinhVien {
    public SinhVienLT(String maSV, int soTinChi) {
        super(maSV, "LT", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA + 2000000L;
    }
}

class QuanLyHocPhi {
    private ArrayList<SinhVien> danhSach = new ArrayList<>();
    private Set<String> maSVDaCo = new HashSet<>();

    public void themSinhVien(String maHe, String maSV, String strTinChi) throws DuLieuKhongHopLeException {
        if (!maHe.equals("CQ") && !maHe.equals("CLC") && !maHe.equals("LT")) {
            throw new DuLieuKhongHopLeException(maSV);
        }
        int tinChi;
        try {
            tinChi = Integer.parseInt(strTinChi);
        } catch (NumberFormatException e) {
            throw new DuLieuKhongHopLeException(maSV);
        }
        if (tinChi < 1 || tinChi > 30) {
            throw new DuLieuKhongHopLeException(maSV);
        }
        if (maSVDaCo.contains(maSV)) {
            throw new DuLieuKhongHopLeException(maSV);
        }

        SinhVien sv;
        if (maHe.equals("CQ")) {
            sv = new SinhVienCQ(maSV, tinChi);
        } else if (maHe.equals("CLC")) {
            sv = new SinhVienCLC(maSV, tinChi);
        } else {
            sv = new SinhVienLT(maSV, tinChi);
        }
        danhSach.add(sv);
        maSVDaCo.add(maSV);
    }

    public void sapXep() {
        Collections.sort(danhSach, new Comparator<SinhVien>() {
            @Override
            public int compare(SinhVien a, SinhVien b) {
                if (a.hocPhi() != b.hocPhi()) {
                    return Long.compare(b.hocPhi(), a.hocPhi());
                }
                return a.getMaSV().compareTo(b.getMaSV());
            }
        });
    }

    public ArrayList<SinhVien> getDanhSach() {
        return danhSach;
    }

    public long tongHocPhi() {
        long tong = 0;
        for (SinhVien sv : danhSach) {
            tong += sv.hocPhi();
        }
        return tong;
    }
}

public class bai3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        QuanLyHocPhi ql = new QuanLyHocPhi();
        ArrayList<String> invalidList = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            String strTinChi = sc.next();
            try {
                ql.themSinhVien(maHe, maSV, strTinChi);
            } catch (DuLieuKhongHopLeException e) {
                invalidList.add("INVALID " + e.getMessage());
            }
        }

        for (String msg : invalidList) {
            System.out.println(msg);
        }

        ql.sapXep();
        for (SinhVien sv : ql.getDanhSach()) {
            System.out.println(sv);
        }
        System.out.println("TONG " + ql.tongHocPhi());
    }
}