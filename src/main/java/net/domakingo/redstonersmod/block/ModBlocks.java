package net.domakingo.redstonersmod.block;

import net.domakingo.redstonersmod.RedstonersMod;
import net.domakingo.redstonersmod.block.custom.LampBlock;
import net.domakingo.redstonersmod.block.custom.PinkLed;
import net.domakingo.redstonersmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RedstonersMod.MOD_ID);

    public static final RegistryObject<Block> LAMP_BLOCK = registerBlock("lamp_block",
            () -> new LampBlock(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS)
                    .strength(0.3f).sound(SoundType.GLASS).lightLevel(state -> state.getValue(LampBlock.LIT) ? 15 : 0)));

    public static final RegistryObject<Block> PINK_LED_BLOCK = registerBlock("pink_led_block",
            () -> new PinkLed(BlockBehaviour.Properties.of(Material.BUILDABLE_GLASS)
                    .strength(0.3f).sound(SoundType.GLASS).noOcclusion().lightLevel(state -> state.getValue(LampBlock.LIT) ? 15 : 0)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
