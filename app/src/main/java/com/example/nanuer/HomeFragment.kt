package com.example.nanuer

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nanuer.databinding.FragmentHomeBinding
import net.daum.mf.map.api.MapCircle
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


class HomeFragment : Fragment(){
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)

//        val mapView = MapView(activity)
//        binding.mapView.addView(mapView)
//
//        val mapPoint = MapPoint.mapPointWithGeoCoord(37.28730797086605, 127.01192716921177)
//
//        mapView.setMapCenterPoint(mapPoint, true)
//
//        val marker = MapPOIItem()
//        marker.itemName = "수원화성"
//        marker.mapPoint = mapPoint
//        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
//        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
//        mapView.addPOIItem(marker)
//
//
//        val circle1 = MapCircle(
//            mapPoint,  // center
//            500,  // radius
//            Color.argb(128, 255, 0, 0),  // strokeColor
//            Color.argb(128, 0, 255, 0) // fillColor
//        )

        return binding.root
    }

}