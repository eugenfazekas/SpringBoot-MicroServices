package com.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.NoRouteFound;
import com.model.AbTestingRoute;
import com.repository.AbTestingRouteRepository;


@Service
public class AbTestingRouteService {
    @Autowired
    private AbTestingRouteRepository abTestingRouteRepository;

    public AbTestingRoute getRoute(String serviceName) {
        AbTestingRoute route = abTestingRouteRepository.findByServiceName(serviceName);

        if (route==null){
            throw new NoRouteFound();
        }

        return route;
    }

    public void saveAbTestingRoute(AbTestingRoute route){

        abTestingRouteRepository.save(route);

    }

    public void updateRouteAbTestingRoute(AbTestingRoute route){
        abTestingRouteRepository.save(route);
    }

    public void deleteRoute(AbTestingRoute route){
        abTestingRouteRepository.delete(route.getServiceName());
    }
}
