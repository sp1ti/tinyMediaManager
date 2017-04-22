/*
 * Copyright 2012 - 2017 Manuel Laggner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tinymediamanager.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Window;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;
import org.tinymediamanager.core.entities.Person;
import org.tinymediamanager.ui.EqualsLayout;
import org.tinymediamanager.ui.IconManager;
import org.tinymediamanager.ui.UTF8Control;

import net.miginfocom.swing.MigLayout;

/**
 * this dialog is used for editing information of a person
 *
 * @author Manuel Laggner
 */
public class PersonEditorDialog extends TmmDialog {
  /** @wbp.nls.resourceBundle messages */
  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("messages", new UTF8Control()); //$NON-NLS-1$

  private final Person                personToEdit;

  private JTextField                  tfName;
  private JTextField                  tfRole;
  private JTextField                  tfImageUrl;

  public PersonEditorDialog(Window owner, String title, Person person) {
    super(owner, title, "personEditor");
    personToEdit = person;

    initComponents();

    tfName.setText(personToEdit.getName());
    tfRole.setText(personToEdit.getRole());
    tfImageUrl.setText(personToEdit.getThumbUrl());
  }

  private void initComponents() {
    getContentPane().setLayout(new BorderLayout(0, 0));
    {
      JPanel panelContent = new JPanel();
      getContentPane().add(panelContent);
      panelContent.setLayout(new MigLayout("", "[][300lp:n,grow][]", "[][][]"));
      {
        JLabel lblNameT = new JLabel(BUNDLE.getString("metatag.name"));
        panelContent.add(lblNameT, "cell 0 0,alignx trailing");

        tfName = new JTextField();
        panelContent.add(tfName, "cell 1 0,growx");
        tfName.setColumns(10);
      }
      {
        JLabel lblRoleT = new JLabel(BUNDLE.getString("metatag.role"));
        panelContent.add(lblRoleT, "cell 0 1,alignx trailing");

        tfRole = new JTextField();
        panelContent.add(tfRole, "cell 1 1,growx");
        tfRole.setColumns(10);
      }
      {
        JLabel lblImageUrlT = new JLabel(BUNDLE.getString("image.url"));
        panelContent.add(lblImageUrlT, "cell 0 2,alignx trailing");

        tfImageUrl = new JTextField();
        panelContent.add(tfImageUrl, "cell 1 2,growx");
        tfImageUrl.setColumns(10);
      }
      {
        JButton btnShowImage = new JButton(IconManager.IMAGE_INV);
        btnShowImage.setToolTipText(BUNDLE.getString("image.show"));
        btnShowImage.addActionListener(e -> {
          if (StringUtils.isNotBlank(tfImageUrl.getText())) {
            // check for valid url
            try {
              URL url = new URL(tfImageUrl.getText());
              Dialog dialog = new ImagePreviewDialog(url.toExternalForm());
              dialog.setVisible(true);
            }
            catch (Exception ignored) {
            }
          }
        });
        panelContent.add(btnShowImage, "cell 2 2");
      }
    }
    {
      JPanel panelButtons = new JPanel();
      getContentPane().add(panelButtons, BorderLayout.SOUTH);
      panelButtons.setLayout(new MigLayout("", "[grow]", "[]"));
      {
        JPanel panel = new JPanel();
        panel.setLayout(new EqualsLayout(5));
        panelButtons.add(panel, "cell 0 0,alignx right");

        JButton btnOk = new JButton(BUNDLE.getString("Button.save"));
        btnOk.addActionListener(e -> {
          personToEdit.setName(tfName.getText());
          personToEdit.setRole(tfRole.getText());
          personToEdit.setThumbUrl(tfImageUrl.getText());
          setVisible(false);
        });
        panel.add(btnOk);

        JButton btnCancel = new JButton(BUNDLE.getString("Button.cancel"));
        btnCancel.addActionListener(e -> setVisible(false));
        panel.add(btnCancel);
      }
    }
  }
}
