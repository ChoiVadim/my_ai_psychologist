from flask import request, jsonify, Blueprint
from werkzeug.security import generate_password_hash, check_password_hash
from flask_jwt_extended import create_access_token, jwt_required, get_jwt_identity
from .models import User, JournalEntry
from .init import db
from .utils import AssistantManager

routes = Blueprint("routes", __name__)

manager = AssistantManager()
manager.set_assistant_id("asst_dJNgrtxvoO4JcqEW396HUog1")
manager.set_thread_id("thread_RhVmbGLaLtvcce1pR1fCK87p")


@routes.route("/register", methods=["POST"])
def register():
    data = request.get_json()
    hashed_password = generate_password_hash(data["password"], method="pbkdf2:sha256")
    new_user = User(username=data["username"], password=hashed_password)
    db.session.add(new_user)
    db.session.commit()
    return jsonify({"message": "Registered successfully"})


@routes.route("/login", methods=["POST"])
def login():
    data = request.get_json()
    user = User.query.filter_by(username=data["username"]).first()
    if not user or not check_password_hash(user.password, data["password"]):
        return jsonify({"message": "Invalid credentials"}), 401
    access_token = create_access_token(identity=user.id)
    return jsonify(access_token=access_token)


@routes.route("/journal", methods=["POST"])
@jwt_required()
def add_journal_entry():
    user_id = get_jwt_identity()
    data = request.get_json()
    new_entry = JournalEntry(user_id=user_id, content=data["content"])

    manager.add_message_to_thread("user", data["content"])

    db.session.add(new_entry)
    db.session.commit()
    return jsonify({"message": "Entry added"})


@routes.route("/chat", methods=["POST"])
@jwt_required()
def chat_with_ai():
    data = request.get_json()

    manager.add_message_to_thread("user", data["message"])
    manager.run_assistant()
    manager.wait_for_completion()
    response = manager.get_response()

    return jsonify({"response": response})
