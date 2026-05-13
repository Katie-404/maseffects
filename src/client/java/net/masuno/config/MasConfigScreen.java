package net.masuno.config;

import net.masuno.MasEffects;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.uku3lig.ukulib.config.option.CyclingOption;
import net.uku3lig.ukulib.config.option.WidgetCreator;
import net.uku3lig.ukulib.config.option.widget.ButtonTab;
import net.uku3lig.ukulib.config.screen.TabbedConfigScreen;

public class MasConfigScreen extends TabbedConfigScreen<MasConfig> {
    public MasConfigScreen(Screen parent){
        super("maseffects.config.title", parent, MasEffects.manager);
    }
    @Override
    protected Tab[] getTabs(MasConfig config) {
        return new Tab[]{new generalConfig(), new MaceConfig(), new HitboxConfig()};
    }
    public class generalConfig extends ButtonTab<MasConfig>
    {
        public generalConfig(){
            super("maseffects.config.generalconfig", MasConfigScreen.this.manager);
        }
        @Override
        public WidgetCreator[] getWidgets(MasConfig config){
            return new WidgetCreator[]{
                    CyclingOption.ofBoolean("maseffects.option.windparticles", config.WindParticles, (v) -> config.WindParticles = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.windparticles"))),
                    CyclingOption.ofBoolean("maseffects.options.customtotemeffect", config.CustomTotemEffect, (v)-> config.CustomTotemEffect = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.customtotemeffect"))),
                    // TODO: TotemEffectOpacity
                    CyclingOption.ofBoolean("maseffects.options.playerdeatheffect", config.PlayerDeathEffect, (v)-> config.PlayerDeathEffect = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.playerdeatheffect"))),
                    CyclingOption.ofBoolean("maseffects.options.pearltrailparticles", config.PearlTrailParticles, (v)-> config.PearlTrailParticles = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.pearltrailparticles"))),
                    //TODO: PearlTrailOpacity
                    CyclingOption.ofBoolean("maseffects.options.glideicon", config.GlideIcon, (v)-> config.GlideIcon = v, OptionInstance.cachedConstantTooltip(Component.translatable("maseffects.tooltips.glideicon")))
            };
        }
    }
    public class MaceConfig extends ButtonTab<MasConfig>
    {
        public MaceConfig() {
        super("maseffects.config.maceconfig", MasConfigScreen.this.manager);
    }
        @Override
        public WidgetCreator[] getWidgets(MasConfig config){
            return new WidgetCreator[]{/*TODO: Put Mace Config stuff options here*/};
        }
    }
    public class HitboxConfig extends ButtonTab<MasConfig>
    {
        public HitboxConfig(){
            super("maseffects.config.hitboxconfig", MasConfigScreen.this.manager);
        }
        @Override
        public WidgetCreator[] getWidgets(MasConfig config){
            return new WidgetCreator[]{/*hitboxconfig*/};
        }
    }
}
