package bgs99c.lab2;

import java.io.FilePermission;
import java.io.SerializablePermission;
import java.net.InetAddress;
import java.net.SocketPermission;
import java.security.BasicPermission;
import java.security.Permission;
import java.util.PropertyPermission;

public class MySecurityManager extends SecurityManager{
    private Object code;
    MySecurityManager(Object pass){
        super();
        code = pass;
    }
    void disable(Object e){
        if (code == e){
            code = null;
        }
    }
    @Override
    public void checkPermission(Permission perm) {
        if(code == null)
            return;
        if(perm instanceof FilePermission && perm.getActions().equals("read")){
            return;
        }
        if(perm instanceof BasicPermission) {
            if(perm instanceof PropertyPermission && !perm.getActions().contains("write")){
                return;
            }
        }
        if (perm instanceof SerializablePermission)
            return;
        if (perm instanceof RuntimePermission && perm.getName().startsWith("getenv")){
            return;
        }
        throw new SecurityException();
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        checkPermission(perm);
    }
}
