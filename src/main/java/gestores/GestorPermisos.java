package gestores;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import entidades.enumerables.TipoPermiso;

public class GestorPermisos {
    private ArrayList<TipoPermiso> listaPermisos;
    private static Map<String, List<TipoPermiso>> permisosRol = new HashMap<>();

    public GestorPermisos() {
        this.listaPermisos = new ArrayList<>();
    }

    public void agregarPermisos(List<TipoPermiso> permisos) {
        this.listaPermisos.addAll(permisos);
    }

    public void agregarPermisos(TipoPermiso permiso) {
        listaPermisos.add(permiso);
    }

    public void listarPermisos() {
        for (TipoPermiso p : listaPermisos) {
            System.out.println("-" + p);
        }
    }

    public static List<TipoPermiso> getPermisosByRol(String rol) {
        return permisosRol.get(rol);
    }

    public void modificarPermisosByRol(String rol, List<TipoPermiso> permisos) {
        permisosRol.put(rol, permisos);
    }

    public ArrayList<TipoPermiso> getListaPermisos() {
        return listaPermisos;
    }
}
