package com.script972.clutchclient.mvp.impl;


import com.script972.clutchclient.model.api.Company;
import com.script972.clutchclient.model.api.Position;
import com.script972.clutchclient.mvp.contracts.GeoListContract;
import com.script972.clutchclient.ui.adapters.GeoListAdapter;
import com.script972.clutchclient.ui.views.GeoDiscountView;

import java.util.ArrayList;
import java.util.List;

public class GeoDiscountPresenterImp implements GeoListContract.Presenter {

    private GeoListContract.View view;
    private GeoListAdapter geoListAdapter;

    public GeoDiscountPresenterImp(GeoDiscountView geoDiscountView) {
        this.view=geoDiscountView;
    }

    /**
     * Method wich get list from server
     */
    @Override
    public void getListGeoCompany() {
        //TODO get from server

        List<Company> lists=new ArrayList<>();
        lists.add(new Company("Addidas", new Position(46.973092, 31.993674), "bla bla bla bla",
                "https://png.kisspng.com/sh/c91a2cbab3ab7a7c8cd124c6a0142cb1/L4Dxd3E5UME4OWM2TpG6ZkmyRbK6VcRmO5JmfKQ7M0mxQ4G9U8M5PGg2TaM8NES1SIi7V8A6Ol91htk=/5a354e3aad2239.3063384715134428747092.png"));
        lists.add(new Company("Владам", new Position(46.969198, 32.003418), "Пиццерия \"Владам\" расположена в самом центре Николаева. К нам всегда легко добраться. ",
                "http://pizza-vladam.com.ua/media/logo/cache/200x139default/vladam-pizza.png"));
        lists.add(new Company("Сильпо", new Position(46.962648, 32.006109), "Одна из крупнейших национальных сетей продовольственных супермаркетов. " +
                "Супермаркет «Сільпо» — это магазин самообслуживания, ассортимент которого насчитывает до 20 000 наименований " +
                "продуктов питания и сопутствующих товаров в зависимости от величины торговой площади магазина.",
                "http://ukraina.zp.ua/assets/uploads/files/567b8-silpo-zaporozhe-ul-charivna-155b1372995207.jpg"));

        lists.add(new Company("Сильпо",  new Position(46.966760, 32.001799),"Одна из крупнейших национальных сетей продовольственных супермаркетов. " +
                "Супермаркет «Сільпо» — это магазин самообслуживания, ассортимент которого насчитывает до 20 000 наименований " +
                "продуктов питания и сопутствующих товаров в зависимости от величины торговой площади магазина.",
                "http://ukraina.zp.ua/assets/uploads/files/567b8-silpo-zaporozhe-ul-charivna-155b1372995207.jpg"));
        // ///

        this.geoListAdapter=new GeoListAdapter(lists);

        view.fillListGeoCompany(geoListAdapter, lists);
        geoListAdapter.notifyDataSetChanged();
    }
}
