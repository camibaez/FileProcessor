/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processor.editor.windows;

import java.io.File;
import java.io.IOException;
import javax.swing.text.StyledDocument;
import org.netbeans.modules.refactoring.api.RefactoringElement;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.util.Exceptions;

/**
 *
 * @author cbaez
 */
public class CodeEditorOpener {
    public void openEditor(File f) {
        try {
            FileObject fo =  FileUtil.toFileObject(f);
            DataObject d = DataObject.find(fo);
            EditorCookie ec = (EditorCookie) d.getLookup().lookup(EditorCookie.class);
            ec.open();
            StyledDocument doc = ec.openDocument();

        } catch (DataObjectNotFoundException ex) {
            Exceptions.printStackTrace(ex);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
