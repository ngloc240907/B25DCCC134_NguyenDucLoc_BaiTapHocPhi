import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

interface MienGiam {
    long tinhGiam(long hocPhi);
}

class MienGiamCS implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 50 / 100;
    }
}

class MienGiamHN implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 70 / 100;
    }
}

class MienGiamHB implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(3000000L, hocPhi);
    }
}

abstract class SinhVien {
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected MienGiam mienGiam;
    protected static final long DON_GIA = 450000L;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
        this.mienGiam = null;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getMaHe() {
        return maHe;
    }

    public void setMienGiam(MienGiam mienGiam) {
        this.mienGiam = mienGiam;
    }

    public abstract long hocPhi();

    public long soTienGiam() {
        if (mienGiam == null) {
            return 0;
        }
        return mienGiam.tinhGiam(hocPhi());
    }

    public long phaiNop() {
        return hocPhi() - soTienGiam();
    }

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi() + " " + soTienGiam() + " " + phaiNop();
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

public class bai4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();
        ArrayList<SinhVien> list = new ArrayList<>();
        Map<String, SinhVien> mapSV = new HashMap<>();

        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            int tinChi = sc.nextInt();
            SinhVien sv = null;
            if (maHe.equals("CQ")) {
                sv = new SinhVienCQ(maSV, tinChi);
            } else if (maHe.equals("CLC")) {
                sv = new SinhVienCLC(maSV, tinChi);
            } else if (maHe.equals("LT")) {
                sv = new SinhVienLT(maSV, tinChi);
            }
            if (sv != null) {
                list.add(sv);
                mapSV.put(maSV, sv);
            }
        }

        int m = sc.nextInt();
        ArrayList<String> notFoundList = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String maSV = sc.next();
            String maCS = sc.next();
            SinhVien sv = mapSV.get(maSV);
            if (sv == null) {
                notFoundList.add("NOT FOUND " + maSV);
            } else {
                if (maCS.equals("CS")) {
                    sv.setMienGiam(new MienGiamCS());
                } else if (maCS.equals("HN")) {
                    sv.setMienGiam(new MienGiamHN());
                } else if (maCS.equals("HB")) {
                    sv.setMienGiam(new MienGiamHB());
                }
            }
        }

        for (String msg : notFoundList) {
            System.out.println(msg);
        }

        for (SinhVien sv : list) {
            System.out.println(sv);
        }

        String[] heList = {"CQ", "CLC", "LT"};
        for (String he : heList) {
            int count = 0;
            long tongPhaiNop = 0;
            for (SinhVien sv : list) {
                if (sv.getMaHe().equals(he)) {
                    count++;
                    tongPhaiNop += sv.phaiNop();
                }
            }
            if (count > 0) {
                System.out.println(he + " " + count + " " + tongPhaiNop);
            }
        }
    }
}