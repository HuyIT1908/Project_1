package com.example.project_1.ThongKe;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_1.DAO.ChiDAO;
import com.example.project_1.DAO.KHchiDAO;
import com.example.project_1.DAO.KhoanNoDAO;
import com.example.project_1.DAO.NguoiDungDAO;
import com.example.project_1.DAO.ThuDAO;
import com.example.project_1.DAO.TietKiemDAO;
import com.example.project_1.Models.KHchi;
import com.example.project_1.Models.KhoanNo;
import com.example.project_1.Models.NguoiDung;
import com.example.project_1.Models.TietKiem;
import com.example.project_1.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ThongKeFragment extends Fragment implements OnChartValueSelectedListener {

    private PieChart mChart;
    private BarChart barChart;
    private NguoiDungDAO nguoiDungDAO;
    private List<NguoiDung> list_Ndung;
    private ChiDAO chiDAO;
    private ThuDAO thuDAO;
    private KhoanNoDAO khoanNoDAO;
    private TietKiemDAO tietKiemDAO;
    private KHchiDAO kHchiDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_ke, container, false);

        chiDAO = new ChiDAO( getActivity() );
        thuDAO = new ThuDAO( getActivity() );
        khoanNoDAO = new KhoanNoDAO( getActivity() );
        tietKiemDAO = new TietKiemDAO(getActivity() );
        kHchiDAO = new KHchiDAO( getActivity() );
        TextView tv_show_tien = view.findViewById(R.id.tv_show_tien);
        nguoiDungDAO = new NguoiDungDAO( getActivity() );
        list_Ndung = new ArrayList<>();
        list_Ndung = nguoiDungDAO.getAllNguoiDung();
        try {
            tv_show_tien.setText("Số tiền :  " + list_Ndung.get(0).getTongSoTien() );
        } catch (Exception ex){
            tv_show_tien.setText("Số tiền :  0" );
        }

//        mChart = (PieChart) view.findViewById(R.id.piechart);
//        mChart.setRotationEnabled(true);
//        Description description = new Description();
//        description.setText("");
//        mChart.setDescription(description );
//        mChart.setHoleRadius(35f);
//        mChart.setTransparentCircleAlpha(0);
//        mChart.setCenterText("Thống Kê Chi Tiêu");
//        mChart.setCenterTextSize(14);
//        mChart.setDrawEntryLabels(true);
//
//        addDataSet(mChart , thuDAO , chiDAO , kHchiDAO , khoanNoDAO , tietKiemDAO , nguoiDungDAO);
//
//        mChart.setOnChartValueSelectedListener(this);
        float[] yData = {
                thuDAO.get_GT("SELECT sum(soTienThu) FROM Thu;"),
                chiDAO.get_GT("SELECT sum(soTienChi) FROM Chi;"),
                kHchiDAO.get_GT("SELECT sum(soTienDuChi) FROM KeHoachChi;"),
                khoanNoDAO.get_GT("SELECT sum(soTienNo) FROM KhoanNo;"),
                tietKiemDAO.get_GT("SELECT sum(soTienTietKiem) FROM TietKiem;")
        };

        barChart = view.findViewById(R.id.barChart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(4f, yData[0] ));
        entries.add(new BarEntry(8f, yData[1] ));
        entries.add(new BarEntry(6f, yData[2] ));
        entries.add(new BarEntry(12f, yData[3] ));
        entries.add(new BarEntry(18f, yData[4] ));

//        entries.add(new BarEntry(6f, 20));
//        entries.add(new BarEntry(8f, 25));
//        entries.add(new BarEntry(10f, 40));
//        entries.add(new BarEntry(12f, 15));
//        entries.add(new BarEntry(14f, 10));

//        for (int i = 0; i < 10; i++) {
//            float value = (float) (Math.random() *100);
//            entries.add(new BarEntry(i , (int) value) );
//        }
//
        BarDataSet dataSet = new BarDataSet(entries, "Thống Kê Chi Tiêu");

        dataSet.setValueTextSize(12);

        List<String> labels = new ArrayList<String>();
        labels.add("Chi");
        labels.add("Kế hoạch");
        labels.add("Thu");
        labels.add("Tiết Kiệm");
        labels.add("Vay");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.MAGENTA);
        colors.add(Color.CYAN);
        colors.add(Color.GRAY);

        dataSet.setColors(colors);

        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setEnabled(true);

        List<LegendEntry> legendEntries = new ArrayList<>();
        int[] mau = {Color.RED , Color.GREEN , Color.MAGENTA , Color.CYAN , Color.GRAY};
        for (int i = 0; i < entries.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = mau[i];
            entry.label = labels.get(i);
            legendEntries.add(entry);
        }

        legend.setCustom(legendEntries);

        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);
        dataSet.setDrawValues( true);
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        BarData data = new BarData(dataSet);
        barChart.setData(data);

//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setValueFormatter(new IndexAxisValueFormatter( labels ) );
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setPosition( XAxis.XAxisPosition.TOP );
////        xAxis.setDrawGridLines(false);
////        xAxis.setDrawAxisLine(false);
//        xAxis.setGranularity(1);
//        xAxis.setGranularityEnabled(true);
////        xAxis.setLabelCount( labels.size() );
////        xAxis.setLabelRotationAngle( 270 );
////        xAxis.setDrawLabels( true);

        barChart.setFitBars( true );
        barChart.animateY(1000);
        barChart.invalidate();

        return view;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
//        dialog_chung(1 , getActivity() ,
//                "Value: " + e.getY() + ", index: " + h.getX() +
//                        ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {

    }

    private static void addDataSet(PieChart pieChart , ThuDAO thuDAO , ChiDAO chiDAO, KHchiDAO kHchiDAO ,
                KhoanNoDAO khoanNoDAO , TietKiemDAO tietKiemDAO , NguoiDungDAO nguoiDungDAO) {
        try {
            List<PieEntry> yEntrys = new ArrayList<>();
            List<String> xEntrys = new ArrayList<>();

            List<NguoiDung> nguoiDungList = new ArrayList<>();
            nguoiDungList = nguoiDungDAO.getAllNguoiDung();
            Integer tong = Integer.parseInt( nguoiDungList.get(0).getTongSoTien() );
//        Log.e(tong + "\t" , "\t" + thuDAO.get_GT("SELECT sum(soTienThu) FROM Thu;")
//        + "\t" + Integer.parseInt(String.valueOf( (Integer.parseInt(thuDAO.get_GT("SELECT sum(soTienThu) FROM Thu;")) / tong) * 100)));
//            float[] yData = {
//                    ( ( thuDAO.get_GT("SELECT sum(soTienThu) FROM Thu;") / tong) * 100 ),
//                    ( ( chiDAO.get_GT("SELECT sum(soTienChi) FROM Chi;") / tong) * 100 ),
//                    ( ( kHchiDAO.get_GT("SELECT sum(soTienDuChi) FROM KeHoachChi;") / tong ) * 100),
//                    ( (khoanNoDAO.get_GT("SELECT sum(soTienNo) FROM KhoanNo;") / tong) * 100),
//                    ( (tietKiemDAO.get_GT("SELECT sum(soTienTietKiem) FROM TietKiem;") / tong) * 100)
//            };

            float[] yData = {
                    thuDAO.get_GT("SELECT sum(soTienThu) FROM Thu;"),
                    chiDAO.get_GT("SELECT sum(soTienChi) FROM Chi;"),
                    kHchiDAO.get_GT("SELECT sum(soTienDuChi) FROM KeHoachChi;"),
                    khoanNoDAO.get_GT("SELECT sum(soTienNo) FROM KhoanNo;"),
                    tietKiemDAO.get_GT("SELECT sum(soTienTietKiem) FROM TietKiem;")
            };

//            float[] yData = {
//                25,
//                25,
//                25,
//                10,
//                15};
            String[] xData = {"Thu", "Chi", "Kế hoạch", "vay" , "Tiết Kiệm"};

            for (int i = 0; i < yData.length; i++) {
                yEntrys.add(new PieEntry(yData[i], xData[i]) );
            }
            for (int i = 0; i < xData.length; i++) {
                xEntrys.add(xData[i]);
            }

            PieDataSet pieDataSet = new PieDataSet(yEntrys, "Quản Lí Chi Tiêu");
            pieDataSet.setSliceSpace(2);
            pieDataSet.setValueTextSize(12);

            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(Color.RED);
            colors.add(Color.GREEN);
            colors.add(Color.MAGENTA);
            colors.add(Color.CYAN);
            colors.add(Color.GRAY);

            pieDataSet.setColors(colors);

            Legend legend = pieChart.getLegend();
            legend.setForm(Legend.LegendForm.CIRCLE);
            legend.setEnabled(true);

            List<LegendEntry> entries = new ArrayList<>();
            int[] mau = {Color.RED , Color.GREEN , Color.MAGENTA , Color.CYAN , Color.GRAY};
            for (int i = 0; i < yEntrys.size(); i++) {
                LegendEntry entry = new LegendEntry();
                entry.formColor = mau[i];
                entry.label = xData[i];
                entries.add(entry);
            }

            legend.setCustom(entries);
//        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

            PieData pieData = new PieData(pieDataSet);
            pieChart.setData(pieData);
            pieData.setValueFormatter(new PercentFormatter());
            pieChart.invalidate();
        } catch (Exception ex){
            Log.e("thong ke Error\t" , ex.toString() );
        }

    }

    private void dialog_chung(Integer so, Context context, String tb) {
        switch (so) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông Báo").setMessage(tb);
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case 1:
                Toast.makeText(context, tb, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}