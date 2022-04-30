package ru.ssau.providerrequest.presenter.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.RequestPoint
import com.yandex.mapkit.RequestPointType.WAYPOINT
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.geometry.SubpolylineHelper
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.transport.TransportFactory
import com.yandex.mapkit.transport.masstransit.MasstransitOptions
import com.yandex.mapkit.transport.masstransit.MasstransitRouter
import com.yandex.mapkit.transport.masstransit.Route
import com.yandex.mapkit.transport.masstransit.SectionMetadata.SectionData
import com.yandex.mapkit.transport.masstransit.Session
import com.yandex.mapkit.transport.masstransit.TimeOptions
import com.yandex.mapkit.transport.masstransit.Transport
import com.yandex.runtime.Error
import ru.ssau.providerrequest.databinding.FragmentMapBinding
import ru.ssau.providerrequest.presenter.base.BaseFragment

class MapFragment : BaseFragment<FragmentMapBinding>(), LocationListener, Session.RouteListener {

    private val args by navArgs<MapFragmentArgs>()
    override fun initBinding(inflater: LayoutInflater): FragmentMapBinding = FragmentMapBinding.inflate(inflater)
    private val locationManager by lazy { requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    private var locationPoint: Point? = null
    private val point by lazy { Point(args.lat.toDouble(), args.long.toDouble()) }

    private var mapObjects: MapObjectCollection? = null
    private var mtRouter: MasstransitRouter? = null
    private var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mapview.map.move(
                CameraPosition(point, 14.0f, 0.0f, 0.0f),
                Animation(Animation.Type.SMOOTH, 5F),
                null
            )
            mapview.map.mapObjects.addPlacemark(point)
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            location.setOnClickListener {
                getCurrentLocation()
            }
            route.setOnClickListener {
                setRoute()
            }
        }
    }

    override fun onStop() {
        locationManager.removeUpdates(this)
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        binding.mapview.onStart()
        MapKitFactory.getInstance().onStart()
        super.onStart()
    }

    private fun setRoute() {
        if (locationPoint != null) {
            mapObjects = binding.mapview.map.mapObjects.addCollection()
            val options = MasstransitOptions(
                ArrayList(),
                ArrayList(),
                TimeOptions()
            )
            val points: MutableList<RequestPoint> = ArrayList()
            points.add(RequestPoint(point, WAYPOINT, null))
            points.add(RequestPoint(locationPoint!!, WAYPOINT, null))
            mtRouter = TransportFactory.getInstance().createMasstransitRouter()
            mtRouter!!.requestRoutes(points, options, this)
        }
    }

    @SuppressLint("ServiceCast")
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermissions()
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0F, this)
        }
    }

    override fun onLocationChanged(p0: Location) {
        locationPoint = Point(p0.latitude, p0.longitude)
        if (count == 0) {
            count()
            count++
        }
        Log.d("LOGGER", Point(p0.latitude, p0.longitude).toString())
        binding.mapview.map.mapObjects.addPlacemark(locationPoint!!)
    }

    private fun count() {
        binding.mapview.map.move(
            CameraPosition(locationPoint!!, 14.0f, 0.0f, 0.0f),
            Animation(Animation.Type.LINEAR, 2F),
            null
        )
    }

    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION
        )
    }

    companion object {

        private const val REQUEST_LOCATION = 1
    }

    override fun onMasstransitRoutes(p0: MutableList<Route>) {
        if (p0.size > 0) {
            for (section in p0[0].sections) {
                drawSection(
                    section.metadata.data,
                    SubpolylineHelper.subpolyline(
                        p0[0].geometry, section.geometry
                    )
                )
            }
        }
    }

    private fun drawSection(
        data: SectionData,
        geometry: Polyline
    ) {
        val polylineMapObject = mapObjects!!.addPolyline(geometry)
        if (data.transports != null) {
            for (transport in data.transports!!) {
                if (transport.line.style != null) {
                    polylineMapObject.strokeColor = transport.line.style!!.color?.or(-0x1000000) ?: return
                }
            }
            val knownVehicleTypes: HashSet<String> = HashSet()
            knownVehicleTypes.add("bus")
            knownVehicleTypes.add("tramway")
            for (transport in data.transports!!) {
                val sectionVehicleType: String = getVehicleType(transport, knownVehicleTypes)
                if (sectionVehicleType == "bus") {
                    polylineMapObject.strokeColor = -0xff0100 // Green
                    return
                } else if (sectionVehicleType == "tramway") {
                    polylineMapObject.strokeColor = -0x10000 // Red
                    return
                }
            }
            polylineMapObject.strokeColor = -0xffff01 // Blue
        } else {
            polylineMapObject.strokeColor = -0x1000000 // Black
        }
    }

    private fun getVehicleType(transport: Transport, knownVehicleTypes: HashSet<String>): String {
        for (type in transport.line.vehicleTypes) {
            if (knownVehicleTypes.contains(type)) {
                return type
            }
        }
        return ""
    }

    override fun onMasstransitRoutesError(p0: Error) {
        Log.d("Logger", p0.toString())
    }
}