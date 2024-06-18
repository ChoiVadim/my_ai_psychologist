from app.init import create_app

app = create_app()

if __name__ == "__main__":
    # Вывод всех зарегистрированных маршрутов
    with app.app_context():
        print(app.url_map)
    app.run(debug=True)
