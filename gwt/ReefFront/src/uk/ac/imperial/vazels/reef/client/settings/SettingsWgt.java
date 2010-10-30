package uk.ac.imperial.vazels.reef.client.settings;

import java.util.HashMap;
import java.util.Map;

import uk.ac.imperial.vazels.reef.client.settings.overlay.SectionList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.StackPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class SettingsWgt extends VerticalPanel {
  private Label statusBar = new Label();
  private StackPanel settingAccordion = new StackPanel();
  private Button refreshBtn = new Button("Refresh");
  private Map<String, SettingsSectionWgt> sectionWgts;

  public SettingsWgt() {
    sectionWgts = new HashMap<String, SettingsSectionWgt>();

    refreshBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        refreshSettings();
      }
    });

    this.add(statusBar);
    this.add(settingAccordion);
    this.add(refreshBtn);
  }

  private void refreshSettings() {
    SettingsManager.getManager().getSections(
        new SettingsManager.RequestHandler<SectionList>() {
          @Override
          public void handle(SectionList reply, Integer code, String msg) {
            if (code == null)
              statusBar.setText("There was an unknown problem with the request...");
            else if (code == 200) {
              refreshSettings(reply);
              statusBar.setText("");
            } else {
              String err = "Problem: " + code;
              if (msg != null)
                err += " - " + msg;
              statusBar.setText(err);
            }
          }
        });
  }

  private void refreshSettings(SectionList sections) {
    settingAccordion.clear();
    HashMap<String, SettingsSectionWgt> newSectionWgts = new HashMap<String, SettingsSectionWgt>();

    for (String section : sections) {
      SettingsSectionWgt wgt = null;

      if (sectionWgts.containsKey(section))
        wgt = sectionWgts.get(section);
      else
        wgt = new SettingsSectionWgt(section);

      newSectionWgts.put(section, wgt);
      wgt.refreshFields();
      settingAccordion.add(wgt, section);
    }

    sectionWgts = newSectionWgts;
  }
}