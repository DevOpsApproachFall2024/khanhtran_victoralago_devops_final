require "rails_helper"
require "webmock/rspec"

RSpec.describe WeatherController, type: :controller do
  describe "GET #index" do
    it "assigns @countries with all common names of ISO3166 countries" do
      get :index
      expect(assigns(:countries)).to include("United States", "Canada", "United Kingdom") # Add specific country names based on ISO3166::Country.all
    end

    it "renders the index template" do
      get :index
      expect(response).to render_template(:index)
    end
  end

  describe "POST #fetch" do
    it "redirects to the weather path with the city parameter" do
      post :fetch, params: { city: "New York" }
      expect(response).to redirect_to(weather_path("New York"))
    end
  end

  describe "GET #show" do
    let(:city) { "London" }

    context "when fetch_weather_data returns data" do
      it "assigns @weather_data and renders the show template" do
        allow_any_instance_of(WeatherController).to receive(:fetch_weather_data).with(city).and_return("Sunny")
        get :show, params: { city: city }
        expect(assigns(:weather_data)).to eq("Sunny")
        expect(response).to render_template(:show)
      end
    end

    context "when fetch_weather_data fails" do
      it "assigns @weather_data as nil and renders the show template" do
        allow_any_instance_of(WeatherController).to receive(:fetch_weather_data).with(city).and_return(nil)
        get :show, params: { city: city }
        expect(assigns(:weather_data)).to be_nil
        expect(response).to render_template(:show)
      end
    end

    context "with mocked HTTP request" do
      let(:city) { "Paris" }

      before do
        stub_request(:get, "http://localhost:8080/weather/#{CGI.escape(city)}")
          .to_return(status: 200, body: { weather: "Rainy" }.to_json, headers: { "Content-Type" => "application/json" })
      end

      it "fetches weather data for the city and assigns it" do
        get :show, params: { city: city }
        expect(assigns(:weather_data)).to eq("weather" => "Rainy")
        expect(response).to render_template(:show)
      end
    end
  end
end
