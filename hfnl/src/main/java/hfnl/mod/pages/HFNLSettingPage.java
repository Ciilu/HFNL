/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * */
package hfnl.mod.pages;

import hfnl.mod.HFNLI18n;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import org.jackhuang.hmcl.setting.ConfigHolder;
import org.jackhuang.hmcl.ui.FXUtils;
import org.jackhuang.hmcl.ui.SVG;
import org.jackhuang.hmcl.ui.animation.ContainerAnimations;
import org.jackhuang.hmcl.ui.animation.TransitionPane;
import org.jackhuang.hmcl.ui.construct.AdvancedListBox;
import org.jackhuang.hmcl.ui.construct.PageAware;
import org.jackhuang.hmcl.ui.construct.TabControl;
import org.jackhuang.hmcl.ui.construct.TabHeader;
import org.jackhuang.hmcl.ui.decorator.DecoratorAnimatedPage;
import org.jackhuang.hmcl.ui.decorator.DecoratorPage;
import org.jackhuang.hmcl.util.logging.Logger;

import static org.jackhuang.hmcl.util.i18n.I18n.i18n;

public class HFNLSettingPage extends DecoratorAnimatedPage implements DecoratorPage, PageAware {
    private final ReadOnlyObjectWrapper<State> state = new ReadOnlyObjectWrapper<>(State.fromTitle(i18n("settings")));
    private final TabHeader tab;
    private final TabHeader.Tab<HFNLAboutPage> aboutTab = new TabControl.Tab<>("aboutPage");
    private final TabHeader.Tab<HFNLModPage> modTab = new TabHeader.Tab<>("modPage");
    private final TransitionPane transitionPane = new TransitionPane();

    public HFNLSettingPage() {
        try {
            ConfigHolder.init();
        } catch (Exception e) {
            Logger.LOG.error("", e);
        }
        aboutTab.setNodeSupplier(HFNLAboutPage::new);
        modTab.setNodeSupplier(HFNLModPage::new);
        tab = new TabHeader(aboutTab, modTab);

        tab.select(aboutTab);

        FXUtils.onChangeAndOperate(tab.getSelectionModel().selectedItemProperty(), newValue -> transitionPane.setContent(newValue.getNode(), ContainerAnimations.FADE));

        {
            //.addNavigationDrawerTab(this.tab, this.settingsTab, I18n.i18n("settings.launcher.general"), SVG.APPLICATION_OUTLINE)
            AdvancedListBox sideBar = new AdvancedListBox()
                    .addNavigationDrawerTab(this.tab, this.aboutTab, HFNLI18n.getString("hfnl.about"), SVG.INFORMATION_OUTLINE)
                    .addNavigationDrawerTab(this.tab, this.modTab, HFNLI18n.getString("hfnl.mod"), SVG.PUZZLE);

            FXUtils.setLimitWidth(sideBar, 200);
            setLeft(sideBar);
        }

        setCenter(transitionPane);
    }

    @Override
    public void onPageShown() {
        tab.onPageShown();
    }

    @Override
    public void onPageHidden() {
        tab.onPageHidden();
    }

    @Override
    public ReadOnlyObjectProperty<State> stateProperty() {
        return state.getReadOnlyProperty();
    }
}
