package bdp.compalytics.app.di;

import bdp.compalytics.service.UidSupplier;
import bdp.compalytics.service.impl.SimpleUidSupplier;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class UidSupplierBinder extends AbstractBinder {
    private final UidSupplier uidSupplier;

    public UidSupplierBinder() {
        uidSupplier = new SimpleUidSupplier();
    }

    public UidSupplier getUidSupplier() {
        return uidSupplier;
    }

    protected void configure() {
        bind(getUidSupplier()).to(UidSupplier.class);
    }
}
