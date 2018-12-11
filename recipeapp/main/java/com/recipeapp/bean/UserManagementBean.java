package com.recipeapp.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.jasypt.util.password.BasicPasswordEncryptor;

import com.recipeapp.dto.UserDto;
import com.recipeapp.service.UserService;
import com.recipeapp.utility.Message;

@ManagedBean(name = "userManagementBean")
@RequestScoped
public class UserManagementBean {

	private UserDto userDto;

	@ManagedProperty(value = "#{userService}")
	private UserService userService;
	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@PostConstruct
	public void init() {
		userDto = new UserDto();
	}

	public String createUser() {
		if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
			if (userService.existRecipe(userDto.getUsername(), userDto.getEmail()) == null) {
				if (userService.create(userDto)) {
					userDto = new UserDto();
					Message.addFlushMessage(Message.bundle.getString("USER_CREATED"), "info");
					return "login?faces-redirect=true";
				} else {
					Message.addMessage(Message.bundle.getString("USER_NOTADDED"), "warn");
				}
			} else {
				Message.addMessage(Message.bundle.getString("USER_EXIST"),"error");

			}
		} else {
			Message.addMessage(Message.bundle.getString("PASSWORD_NOTEQUAL"), "error");

		}
		return "";
	}

	public String updateUser() {
		if (userService.existRecipe(userDto.getUsername(), "") == null) {
			userBean.getUserDto().setUsername(userDto.getUsername());
			if (userService.update(userBean.getUserDto())) {
				Message.addFlushMessage(Message.bundle.getString("USER_UPDATED"), "info");
				return "myRecipeManagement?faces-redirect=true";
			} else {
				Message.addMessage(Message.bundle.getString("USER_NOTEDITED"), "warn");
			}
		}
		return "";

	}

	public String changePassword() {
		try {
			BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
			if (userDto.getConfirmPassword().equals(userDto.getPassword())) {
				if (encryptor.checkPassword(userDto.getOldPassword(), userBean.getUserDto().getPassword())) {
					if (!userDto.getOldPassword().equals(userDto.getPassword())) {
						String newPassword= userDto.getPassword();
						if (userService.update(newPassword,userBean.getUserDto().getId())) {
							userBean.getUserDto().setPassword(userDto.getPassword());
							Message.addFlushMessage(Message.bundle.getString("PASSWORD_CHANGED"), "info");
							userDto = new UserDto();
							return "recipeManagement?faces-redirect=true";
						} else {
							Message.addMessage(Message.bundle.getString("PASSWORD_NOTCHANGED"), "error");
						}
					} else {
						Message.addMessage(Message.bundle.getString("NO_CHANGES"), "warn");

					}
				} else {
					Message.addMessage(Message.bundle.getString("PASSWORD_NOTEQUAL1"), "warn");
				}

			} else {
				Message.addMessage(Message.bundle.getString("PASSWORD_NOTEQUAL"), "warn");
			}

			return "";
		} catch (Exception e) {
			return "";
		}

	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
