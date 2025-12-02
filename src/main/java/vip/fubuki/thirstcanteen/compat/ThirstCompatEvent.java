package vip.fubuki.thirstcanteen.compat;

import dev.ghen.thirst.foundation.common.event.RegisterThirstValueEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import vip.fubuki.thirstcanteen.common.item.Canteen;
import vip.fubuki.thirstcanteen.config.ThirstCanteenConfig;
import vip.fubuki.thirstcanteen.mixin.MixinThirstHelper;
import vip.fubuki.thirstcanteen.registry.ThirstCanteenItem;

public class ThirstCompatEvent {
    /** Just make them be recognized as drink, don't mind the detail
     *  See #{@link MixinThirstHelper}
     */
    @SubscribeEvent
    public static void registerDrinks(RegisterThirstValueEvent event){
        ThirstCanteenItem.ITEMS.getEntries().forEach(itemRegistryObject ->{
            if(itemRegistryObject.get() instanceof Canteen canteen){
                event.addDrink(canteen, ThirstCanteenConfig.THIRST_RESTORE_EACH_SIP.get().intValue(),ThirstCanteenConfig.QUENCHED_RESTORE_EACH_SIP.get().intValue());
                event.addContainer(canteen);
            }
        } );
    }

    public static void register(IEventBus modBus) {
        modBus.register(ThirstCompatEvent.class);
    }
}
