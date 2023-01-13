package com.example.assessment_3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.assessment_3.databinding.ActivityGeneralInfoBinding;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.delegates.MapPluginProviderDelegate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeneralInfoActivity extends AppCompatActivity {
    private ActivityGeneralInfoBinding binding;
    private MapView mapView;
    final String CHART_URL = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);

        //code for mapbox
        final Point point = Point.fromLngLat(145.05, -37.85 );
        mapView = findViewById(R.id.mapView);
        CameraOptions cameraPosition = new CameraOptions.Builder()
                .zoom(15.0)
                .center(point)
                .build();
        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS);
        mapView.getMapboxMap().setCamera(cameraPosition);
        //code for marker
        AnnotationPlugin annotationAPI = AnnotationPluginImplKt.getAnnotations((MapPluginProviderDelegate)mapView);
        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationAPI,mapView);
        @SuppressLint("ResourceType") PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(145.05, -37.85))
                .withIconImage(getString(R.drawable.icon));
        pointAnnotationManager.create(pointAnnotationOptions);

        binding = ActivityGeneralInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //menu view binding
        binding.home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralInfoActivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });
        //code for graph
        List<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(0, 800));
        barEntries.add(new BarEntry(1, 700));
        barEntries.add(new BarEntry(2, 300));
        BarDataSet barDataSet = new BarDataSet(barEntries, "");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Hawaiian", "Supreme", "BBQ Chicken"));
        binding.barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisValues));
        BarData barData = new BarData(barDataSet);
        binding.barChart.setData(barData);
        barData.setBarWidth(1.0f);
        binding.barChart.setVisibility(View.VISIBLE);
        binding.barChart.animateY(4000);
        //description will be displayed as "Description Label" if not provided
        Description description = new Description();
        description.setText("Most Popular Pizzas");
        binding.barChart.setDescription(description);
        //refresh the chart
        binding.barChart.invalidate();
    }
}