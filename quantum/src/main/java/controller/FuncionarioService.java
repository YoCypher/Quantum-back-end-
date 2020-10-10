package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.FuncionarioDAO;
import model.Funcionario;

@WebServlet ("/api/funcionarios/*")
public class FuncionarioService extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public FuncionarioService() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// GET BY ID
				String pathInfo = request.getPathInfo();

				if (pathInfo != null) {
					String[] params = pathInfo.split("/");

					if (params.length > 0) {
						Funcionario funcionario = FuncionarioDAO.getFuncionario(Integer.parseInt(params[1]));

						if (funcionario != null) {
							JSONObject jsonObject = new JSONObject();

							jsonObject.put("id", funcionario.getId());
							jsonObject.put("nome", funcionario.getNome());
							jsonObject.put("endereco", funcionario.getEndereco());
							jsonObject.put("cidade", funcionario.getCidade());
							jsonObject.put("estado", funcionario.getEstado());
							jsonObject.put("cep", funcionario.getCep());
							jsonObject.put("salario", funcionario.getSalario());

							response.setContentType("application/json");
							response.setCharacterEncoding("UTF-8");
							response.getWriter().print(jsonObject.toString());
							response.getWriter().flush();
						}
						return;
					}
				}
				
				// GET ALL
				List<Funcionario> list = FuncionarioDAO.getAllFuncionarios();

				try {
					JSONArray jArray = new JSONArray();

					for (Funcionario funcionario : list) {
						JSONObject jsonObject = new JSONObject();

						jsonObject.put("id", funcionario.getId());
						jsonObject.put("nome", funcionario.getNome());
						jsonObject.put("endereco", funcionario.getEndereco());
						jsonObject.put("cidade", funcionario.getCidade());
						jsonObject.put("estado", funcionario.getEstado());
						jsonObject.put("cep", funcionario.getCep());
						jsonObject.put("salario", funcionario.getSalario());

						jArray.put(jsonObject);
					}

					response.setContentType("application/json");
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(jArray.toString());
					response.getWriter().flush();
				} catch (Exception e) {

				}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// UPDATE BY ID
        String pathInfo = request.getPathInfo();
 
        if (pathInfo != null) {
            String[] params = pathInfo.split("/");
 
            if (params.length > 0) {
                StringBuffer jb = new StringBuffer();
                String line = null;
                try {
                    BufferedReader reader = request.getReader();
                    while ((line = reader.readLine()) != null)
                        jb.append(line);
                } catch (Exception e) {
                }
 
                Funcionario funcionario = null;
                JSONObject jsonObject = null;
 
                try {
                    // Request
                    jsonObject = new JSONObject(jb.toString());
                    funcionario = FuncionarioDAO.updateFuncionario(Integer.parseInt(params[1]),
                    		jsonObject.getString("nome"),
                    		jsonObject.getString("endereco"),
                    		jsonObject.getString("cidade"),
                    		jsonObject.getString("estado"),
                    		jsonObject.getString("cep"),
                    		jsonObject.getDouble("salario"));
                                   
                    // Response
                    jsonObject.put("id", funcionario.getId());
					jsonObject.put("nome", funcionario.getNome());
					jsonObject.put("endereco", funcionario.getEndereco());
					jsonObject.put("cidade", funcionario.getCidade());
					jsonObject.put("estado", funcionario.getEstado());
					jsonObject.put("cep", funcionario.getCep());
					jsonObject.put("salario", funcionario.getSalario());
 
                } catch (JSONException e) {
                }
 
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(jsonObject.toString());
                response.getWriter().flush();
            }
        }
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// DELETE BY ID
        String pathInfo = request.getPathInfo();
 
        if (pathInfo != null) {
            String[] params = pathInfo.split("/");
 
            if (params.length > 0) {
                FuncionarioDAO.deleteFuncionario(Integer.parseInt(params[1]));
 
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().flush();
            }
        }
	}
}
